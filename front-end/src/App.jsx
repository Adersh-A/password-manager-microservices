import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import LoginPage from './pages/loginPage'
import HomePage from './pages/homePage'
import ErrorPage from './pages/ErrorPage'
import './index.css';
function App() {
 
  return (
    <Router>
      <Routes>
        <Route path="/login" element={<LoginPage />} />
        <Route path="/home" element={<HomePage />} />
        <Route path="/error" element={<ErrorPage />} />
        <Route path="*" element={<LoginPage />} /> {/* Redirect unknown routes */}
      </Routes>
    </Router>
  )
}

export default App
