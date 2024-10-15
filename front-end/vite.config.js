import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import os from 'os'

// Function to determine if the host is localhost
const isLocalhost = () => {
  const hostname = os.hostname().toLowerCase()
  return hostname.includes('local')
}

console.log('isLocalhost:', isLocalhost())

const targetApi = isLocalhost() ? 'http://localhost:8080' : 'http://api:8080'
const targetAuth = isLocalhost() ? 'http://localhost:8081' : 'http://auth:8081'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  preview: {
    port: 5173,
    strictPort: true,
  },
  server: {
    port: 5173,
    strictPort: true,
    host: true,
    origin: "http://0.0.0.0:5173",
    proxy: {
      '/api': {
        target: targetApi,
        changeOrigin: true,
      },
      '/account': {
        target: targetAuth,
        changeOrigin: true,
      },
    },
  },
})