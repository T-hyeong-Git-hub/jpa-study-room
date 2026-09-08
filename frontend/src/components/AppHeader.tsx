import { Link, NavLink } from 'react-router-dom'
import { useAuth } from '../auth/useAuth'

export function AppHeader() {
  const { session, isAuthenticated } = useAuth()

  return (
    <header className="app-header">
      <Link className="brand" to="/" aria-label="JPA Study Room 홈">
        <span className="brand-mark">J</span>
        <span>JPA Study Room</span>
      </Link>
      <nav className="main-nav" aria-label="주요 메뉴">
        <NavLink to="/rooms">스터디룸</NavLink>
        <NavLink to="/reservations">내 예약</NavLink>
        {isAuthenticated
          ? <span className="member-name">{session?.name}님</span>
          : <Link className="login-button" to="/login">로그인</Link>}
      </nav>
    </header>
  )
}
