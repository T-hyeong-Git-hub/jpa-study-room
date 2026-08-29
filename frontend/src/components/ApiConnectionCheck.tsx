import { useState } from 'react'
import { ApiError } from '../api/client'
import { getRooms } from '../api/rooms'

type ConnectionState = 'idle' | 'loading' | 'connected' | 'error'

export function ApiConnectionCheck() {
  const [state, setState] = useState<ConnectionState>('idle')
  const [message, setMessage] = useState('')

  const checkConnection = async () => {
    setState('loading')
    setMessage('Spring Boot에 요청을 보내는 중입니다.')

    try {
      await getRooms()
      setState('connected')
      setMessage('React가 Spring Boot의 응답을 정상적으로 받았습니다.')
    } catch (error) {
      if (error instanceof ApiError && (error.status === 401 || error.status === 403)) {
        setState('connected')
        setMessage(`Spring Boot 연결 성공: 인증 전이므로 ${error.status} 응답이 정상입니다.`)
        return
      }

      setState('error')
      setMessage(error instanceof Error ? error.message : '알 수 없는 오류가 발생했습니다.')
    }
  }

  return (
    <section className="connection-check" aria-labelledby="connection-title">
      <div>
        <p className="eyebrow">STEP 5 / API CONNECTION</p>
        <h2 id="connection-title">React와 Spring Boot 연결 확인</h2>
        <p>같은 <code>/api/rooms</code> 주소가 실행 환경에 맞는 중간 서버를 거쳐 Spring Boot로 전달됩니다.</p>
      </div>
      <button className="connection-button" type="button" onClick={checkConnection} disabled={state === 'loading'}>
        {state === 'loading' ? '확인 중...' : '백엔드 연결 확인'}
      </button>
      {message && <p className={`connection-result ${state}`} role="status">{message}</p>}
    </section>
  )
}
