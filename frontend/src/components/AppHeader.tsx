export function AppHeader() {
  return (
    <header className="app-header">
      <a className="brand" href="/" aria-label="JPA Study Room 홈">
        <span className="brand-mark">J</span>
        <span>JPA Study Room</span>
      </a>
      <nav className="main-nav" aria-label="주요 메뉴">
        <a href="#rooms">스터디룸</a>
        <a href="#reservations">내 예약</a>
        <button type="button" className="login-button">로그인</button>
      </nav>
    </header>
  )
}
