import { type ChangeEvent, type FormEvent, useState } from 'react'
import { Link } from 'react-router-dom'
import { ApiError } from '../api/client'
import { signup, type SignupRequest, type SignupResponse } from '../api/members'

type SignupErrors = Partial<Record<keyof SignupRequest, string>>

const initialForm: SignupRequest = { name: '', email: '', password: '' }

function validateSignup(form: SignupRequest): SignupErrors {
  const errors: SignupErrors = {}

  if (!form.name.trim()) errors.name = '이름을 입력해주세요.'
  if (!form.email.trim()) {
    errors.email = '이메일을 입력해주세요.'
  } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(form.email)) {
    errors.email = '올바른 이메일 형식을 입력해주세요.'
  }
  if (!form.password) errors.password = '비밀번호를 입력해주세요.'

  return errors
}

export function SignupPage() {
  const [form, setForm] = useState<SignupRequest>(initialForm)
  const [errors, setErrors] = useState<SignupErrors>({})
  const [result, setResult] = useState<SignupResponse | null>(null)
  const [serverError, setServerError] = useState('')
  const [isSubmitting, setIsSubmitting] = useState(false)

  const handleChange = (event: ChangeEvent<HTMLInputElement>) => {
    const field = event.target.name as keyof SignupRequest
    setForm((currentForm) => ({ ...currentForm, [field]: event.target.value }))
    setErrors((currentErrors) => ({ ...currentErrors, [field]: undefined }))
    setResult(null)
    setServerError('')
  }

  const handleSubmit = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault()
    const nextErrors = validateSignup(form)

    if (Object.keys(nextErrors).length > 0) {
      setErrors(nextErrors)
      return
    }

    setIsSubmitting(true)
    setServerError('')

    try {
      const member = await signup({
        name: form.name.trim(),
        email: form.email.trim(),
        password: form.password,
      })
      setResult(member)
      setForm(initialForm)
    } catch (error) {
      setServerError(error instanceof ApiError ? error.message : '서버에 연결하지 못했습니다.')
    } finally {
      setIsSubmitting(false)
    }
  }

  return (
    <section className="auth-page" aria-labelledby="signup-title">
      <div className="auth-intro">
        <p className="eyebrow">CREATE ACCOUNT</p>
        <h1 id="signup-title">회원가입</h1>
        <p>스터디룸을 예약하고 나의 예약 내역을 관리해보세요.</p>
      </div>

      <form className="auth-form" onSubmit={handleSubmit} noValidate>
        <label className="form-field">
          <span>이름</span>
          <input type="text" name="name" value={form.name} onChange={handleChange} placeholder="이름을 입력하세요" autoComplete="name" aria-invalid={Boolean(errors.name)} />
          {errors.name && <small className="field-error">{errors.name}</small>}
        </label>
        <label className="form-field">
          <span>이메일</span>
          <input type="email" name="email" value={form.email} onChange={handleChange} placeholder="example@email.com" autoComplete="email" aria-invalid={Boolean(errors.email)} />
          {errors.email && <small className="field-error">{errors.email}</small>}
        </label>
        <label className="form-field">
          <span>비밀번호</span>
          <input type="password" name="password" value={form.password} onChange={handleChange} placeholder="비밀번호를 입력하세요" autoComplete="new-password" aria-invalid={Boolean(errors.password)} />
          {errors.password && <small className="field-error">{errors.password}</small>}
        </label>

        <button className="auth-submit" type="submit" disabled={isSubmitting}>
          {isSubmitting ? '가입하는 중...' : '회원가입'}
        </button>
        {serverError && <p className="form-message error" role="alert">{serverError}</p>}
        {result && (
          <div className="form-message success" role="status">
            <strong>{result.name}님, 가입이 완료됐습니다.</strong>
            <span>{result.email}</span>
            <Link to="/login">로그인하러 가기</Link>
          </div>
        )}
        <p className="auth-switch">이미 계정이 있나요? <Link to="/login">로그인</Link></p>
      </form>
    </section>
  )
}
