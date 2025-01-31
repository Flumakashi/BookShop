import { useState } from "react";
import { registerUser } from "../services/api";
import { useNavigate } from "react-router-dom";

const RegisterPage = () => {
    const [formData, setFormData] = useState({ username: "", email: "", password: "", confirmPassword: "" });
    const [error, setError] = useState("");
    const navigate = useNavigate();

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (formData.password !== formData.confirmPassword) {
            setError("Пароли не совпадают!");
            return;
        }

        try {
            await registerUser(formData);
            navigate("/login");
        } catch (err) {
            setError(err.message || "Ошибка регистрации");
        }
    };

    return (
        <div>
            <h1>Регистрация</h1>
            <form onSubmit={handleSubmit}>
                <input type="text" name="username" value={formData.username} onChange={handleChange} required />
                <input type="email" name="email" value={formData.email} onChange={handleChange} required />
                <input type="password" name="password" value={formData.password} onChange={handleChange} required />
                <input type="password" name="confirmPassword" value={formData.confirmPassword} onChange={handleChange} required />
                {error && <p>{error}</p>}
                <button type="submit">Зарегистрироваться</button>
            </form>
        </div>
    );
};

export default RegisterPage;