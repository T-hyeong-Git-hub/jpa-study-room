export interface ApiResponse<T> {
  success: boolean
  data: T
  message: string | null
}

export class ApiError extends Error {
  public readonly status: number

  constructor(
    message: string,
    status: number,
  ) {
    super(message)
    this.name = 'ApiError'
    this.status = status
  }
}

const API_BASE_URL = '/api'

export async function apiRequest<T>(
  path: string,
  options: RequestInit = {},
): Promise<T> {
  const response = await fetch(`${API_BASE_URL}${path}`, {
    ...options,
    headers: {
      Accept: 'application/json',
      ...(options.body ? { 'Content-Type': 'application/json' } : {}),
      ...options.headers,
    },
  })

  const contentType = response.headers.get('content-type')
  const body: ApiResponse<T> | null = contentType?.includes('application/json')
    ? await response.json()
    : null

  if (!response.ok) {
    throw new ApiError(body?.message ?? `API 요청에 실패했습니다. (${response.status})`, response.status)
  }

  if (!body?.success) {
    throw new ApiError(body?.message ?? 'API 응답 형식을 확인할 수 없습니다.', response.status)
  }

  return body.data
}
