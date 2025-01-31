import { Link } from "react-router-dom";
import { useContext } from "react";
import AuthContext from "../context/AuthContext";
import "./Navbar.css";

const Navbar = () => {
    const { isAuthenticated, logout } = useContext(AuthContext);

    return (
        <nav>
            <Link to="/">Главная</Link>
            {isAuthenticated ? (
                <>
                    <Link to="/profile">Профиль</Link>
                    <Link to="/create-book">Добавить объявление</Link>
                    <Link to="/book/:id">Посмотреть свое объявление</Link>
                    <button onClick={logout}>Выйти</button>
                </>
            ) : (
                <>
                    <Link to="/login">Вход</Link>
                    <Link to="/register">Регистрация</Link>
                </>
            )}
        </nav>
    );
};

export default Navbar;