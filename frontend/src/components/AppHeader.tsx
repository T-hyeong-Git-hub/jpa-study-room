import { Link, NavLink } from 'react-router-dom'

export function AppHeader() {
  return (
    <header className="app-header">
      <Link className="brand" to="/" aria-label="JPA Study Room 홈">
        <span className="brand-mark">J</span>
        <span>JPA Study Room</span>
      </Link>
      <nav className="main-nav" aria-label="주요 메뉴">
        <NavLink to="/rooms">스터디룸</NavLink>
        <NavLink to="/reservations">내 예약</NavLink>
        <Link className="login-button" to="/login">로그인</Link>
      </nav>
    </header>
  )
}
