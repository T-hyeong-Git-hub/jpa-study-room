import { apiRequest } from './client'

export interface SignupRequest {
  name: string
  email: string
  password: string
}

export interface SignupResponse {
  memberId: number
  email: string
  name: string
}

export interface LoginRequest {
  email: string
  password: string
}

export interface LoginResponse {
  memberId: number
  email: string
  name: string
  accessToken: string
  refreshToken: string
}

export function signup(request: SignupRequest) {
  return apiRequest<SignupResponse>('/members/signup', {
    method: 'POST',
    body: JSON.stringify(request),
  })
}

export function login(request: LoginRequest) {
  return apiRequest<LoginResponse>('/members/login', {
    method: 'POST',
    body: JSON.stringify(request),
  })
}
