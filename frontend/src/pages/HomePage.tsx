import { useState } from 'react'
import { ApiConnectionCheck } from '../components/ApiConnectionCheck'
import { FeatureCard } from '../components/FeatureCard'

const features = [
  { icon: '👤', title: '간편한 회원 관리', description: '회원가입과 로그인으로 나만의 예약 정보를 안전하게 관리합니다.' },
  { icon: '🏠', title: '스터디룸 둘러보기', description: '스터디룸의 이름과 수용 인원을 확인하고 알맞은 공간을 고릅니다.' },
  { icon: '📅', title: '빠른 예약', description: '원하는 날짜와 시간을 선택해 겹치지 않도록 예약합니다.' },
]

export function HomePage() {
  const [message, setMessage] = useState('')

  return (
    <>
      <section className="hero" aria-labelledby="hero-title">
        <p className="eyebrow">JPA STUDY ROOM</p>
        <h1 id="hero-title">공부에 집중할 수 있는 공간을<br />간편하게 예약하세요</h1>
        <p className="hero-description">회원가입부터 스터디룸 선택과 예약까지, 하나씩 배우며 완성하는 예약 서비스입니다.</p>
        <button className="primary-button" type="button" onClick={() => setMessage('준비 완료! 다음 단계에서 회원가입 화면을 연결해요.')}>학습 시작하기</button>
        {message && <p className="learning-message">{message}</p>}
      </section>

      <section className="features" aria-label="주요 기능">
        {features.map((feature) => <FeatureCard key={feature.title} {...feature} />)}
      </section>
      <ApiConnectionCheck />
    </>
  )
}
