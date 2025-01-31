import { useEffect, useState } from "react";

const ProfilePage = () => {
    const [user, setUser] = useState(null);

    useEffect(() => {
        const fetchUser = async () => {
            try {
                const token = localStorage.getItem("token");
                const response = await fetch("http://localhost:8080/api/users/me", {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });

                if (!response.ok) {
                    throw new Error("Ошибка при загрузке данных пользователя");
                }

                const data = await response.json();
                setUser(data);
            } catch (error) {
                console.error("Ошибка:", error);
            }
        };

        fetchUser();
    }, []);

    if (!user) {
        return <p>Загрузка...</p>;
    }

    return (
        <div>
            <h1>Профиль</h1>
            <p><strong>Имя:</strong> {user.username}</p>
            <p><strong>Email:</strong> {user.email}</p>
        </div>
    );
};

export default ProfilePage;