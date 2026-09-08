import { type ChangeEvent, type FormEvent, useState } from 'react'
import { Link, Navigate, useLocation, useNavigate } from 'react-router-dom'
import { ApiError } from '../api/client'
import { login, type LoginRequest } from '../api/members'
import { useAuth } from '../auth/useAuth'

const initialForm: LoginRequest = { email: '', password: '' }

export function LoginPage() {
  const [form, setForm] = useState<LoginRequest>(initialForm)
  const [errorMessage, setErrorMessage] = useState('')
  const [isSubmitting, setIsSubmitting] = useState(false)
  const { isAuthenticated, signIn } = useAuth()
  const location = useLocation()
  const navigate = useNavigate()

  const handleChange = (event: ChangeEvent<HTMLInputElement>) => {
    setForm((currentForm) => ({ ...currentForm, [event.target.name]: event.target.value }))
    setErrorMessage('')
  }

  const handleSubmit = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault()
    setIsSubmitting(true)
    setErrorMessage('')

    try {
      const loginResponse = await login(form)
      signIn(loginResponse)
      const previousPath = (location.state as { from?: { pathname?: string } } | null)?.from?.pathname
      navigate(previousPath ?? '/rooms', { replace: true })
    } catch (error) {
      setErrorMessage(error instanceof ApiError ? error.message : '서버에 연결하지 못했습니다.')
    } finally {
      setIsSubmitting(false)
    }
  }

  if (isAuthenticated) return <Navigate to="/rooms" replace />

  return (
    <section className="auth-page" aria-labelledby="login-title">
      <div className="auth-intro">
        <p className="eyebrow">MEMBER LOGIN</p>
        <h1 id="login-title">로그인</h1>
        <p>가입한 이메일과 비밀번호로 예약 서비스를 시작하세요.</p>
      </div>

      <form className="auth-form" onSubmit={handleSubmit}>
        <label className="form-field">
          <span>이메일</span>
          <input type="email" name="email" value={form.email} onChange={handleChange} placeholder="example@email.com" autoComplete="email" required />
        </label>
        <label className="form-field">
          <span>비밀번호</span>
          <input type="password" name="password" value={form.password} onChange={handleChange} placeholder="비밀번호를 입력하세요" autoComplete="current-password" required />
        </label>

        <button className="auth-submit" type="submit" disabled={isSubmitting}>
          {isSubmitting ? '로그인하는 중...' : '로그인'}
        </button>
        {errorMessage && <p className="form-message error" role="alert">{errorMessage}</p>}
        <p className="auth-switch">아직 계정이 없나요? <Link to="/signup">회원가입</Link></p>
      </form>
    </section>
  )
}
