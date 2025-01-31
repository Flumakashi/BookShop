import { useState, useContext } from "react";
import { loginUser } from "../services/api";
import { useNavigate } from "react-router-dom";
import AuthContext from "../context/AuthContext";

const LoginPage = () => {
    const [formData, setFormData] = useState({ email: "", password: "" });
    const [error, setError] = useState("");
    const navigate = useNavigate();
    const { login } = useContext(AuthContext); // Получаем login из контекста

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await loginUser(formData);
            login(response.token); // Вызываем login, чтобы обновить состояние
            navigate("/profile");
        } catch (err) {
            setError(err.message || "Ошибка входа");
        }
    };

    return (
        <div>
            <h1>Вход</h1>
            <form onSubmit={handleSubmit}>
                <input type="email" name="email" value={formData.email} onChange={handleChange} required />
                <input type="password" name="password" value={formData.password} onChange={handleChange} required />
                {error && <p>{error}</p>}
                <button type="submit">Войти</button>
            </form>
        </div>
    );
};

export default LoginPage;