import { createContext } from 'react'
import type { LoginResponse } from '../api/members'
import type { AuthSession } from './storage'

export interface AuthContextValue {
  session: AuthSession | null
  isAuthenticated: boolean
  signIn: (loginResponse: LoginResponse) => void
}

export const AuthContext = createContext<AuthContextValue | null>(null)
