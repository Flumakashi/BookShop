import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import HomePage from "../pages/HomePage";
import LoginPage from "../pages/LoginPage";
import RegisterPage from "../pages/RegisterPage";
import ProfilePage from "../pages/ProfilePage";
import CreateBookPage from "../pages/CreateBookPage";
import BookPage from "../pages/BookPage";
import Navbar from "../components/Navbar";

const AppRouter = () => {
    return (
        <Router>
            <Navbar />
            <Routes>
                <Route path="/" element={<HomePage />} />
                <Route path="/login" element={<LoginPage />} />
                <Route path="/register" element={<RegisterPage />} />
                <Route path="/profile" element={<ProfilePage />} />
                <Route path="/create-book" element={<CreateBookPage />} />
                <Route path="/book/:id" element={<BookPage />} />
            </Routes>
        </Router>
    );
};

export default AppRouter;