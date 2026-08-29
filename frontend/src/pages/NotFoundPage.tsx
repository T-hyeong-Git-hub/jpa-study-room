import { Link } from 'react-router-dom'

export function NotFoundPage() {
  return (
    <section className="page-section not-found">
      <p className="eyebrow">404 NOT FOUND</p>
      <h1>페이지를 찾을 수 없습니다</h1>
      <p>입력한 주소가 올바른지 확인하거나 홈으로 돌아가세요.</p>
      <Link className="primary-link" to="/">홈으로 돌아가기</Link>
    </section>
  )
}
