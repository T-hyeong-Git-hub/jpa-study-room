import type { LoginResponse } from '../api/members'

const AUTH_STORAGE_KEY = 'jpa-study-room.auth'

export type AuthSession = LoginResponse

export function readAuthSession(): AuthSession | null {
  const storedSession = sessionStorage.getItem(AUTH_STORAGE_KEY)
  if (!storedSession) return null

  try {
    const session = JSON.parse(storedSession) as AuthSession
    return session.accessToken && session.refreshToken ? session : null
  } catch {
    sessionStorage.removeItem(AUTH_STORAGE_KEY)
    return null
  }
}

export function saveAuthSession(session: AuthSession) {
  sessionStorage.setItem(AUTH_STORAGE_KEY, JSON.stringify(session))
}

export function getAccessToken() {
  return readAuthSession()?.accessToken ?? null
}
