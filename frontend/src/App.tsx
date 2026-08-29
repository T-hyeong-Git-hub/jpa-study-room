import { Route, Routes } from 'react-router-dom'
import './App.css'
import { AppHeader } from './components/AppHeader'
import { HomePage } from './pages/HomePage'
import { LoginPage } from './pages/LoginPage'
import { NotFoundPage } from './pages/NotFoundPage'
import { ReservationsPage } from './pages/ReservationsPage'
import { RoomsPage } from './pages/RoomsPage'

function App() {
  return (
    <div className="app-shell">
      <AppHeader />
      <main>
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/rooms" element={<RoomsPage />} />
          <Route path="/reservations" element={<ReservationsPage />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="*" element={<NotFoundPage />} />
        </Routes>
      </main>
    </div>
  )
}

export default App
