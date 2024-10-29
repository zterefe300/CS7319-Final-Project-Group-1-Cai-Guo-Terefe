import { RouterProvider } from "react-router-dom"

import routes from "./routes/routes"

import './App.scss'

function App() {
  return (
    <main>
      <RouterProvider router={routes} />
    </main>
  )
}

export default App
