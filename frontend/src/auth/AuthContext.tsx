import { type ReactNode, useState } from 'react'
import type { LoginResponse } from '../api/members'
import { readAuthSession, saveAuthSession, type AuthSession } from './storage'
import { AuthContext } from './context'

export function AuthProvider({ children }: { children: ReactNode }) {
  const [session, setSession] = useState<AuthSession | null>(() => readAuthSession())

  const signIn = (loginResponse: LoginResponse) => {
    saveAuthSession(loginResponse)
    setSession(loginResponse)
  }

  return (
    <AuthContext.Provider value={{ session, isAuthenticated: Boolean(session?.accessToken), signIn }}>
      {children}
    </AuthContext.Provider>
  )
}
