import { apiRequest } from './client'

export interface Room {
  roomId: number
  name: string
  capacity: number
}

export function getRooms() {
  return apiRequest<Room[]>('/rooms')
}
