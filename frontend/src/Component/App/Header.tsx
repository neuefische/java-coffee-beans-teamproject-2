import axios from "axios";
import {useEffect, useState} from "react";

export default function Header() {
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [userName, setUserName] = useState('');

    function login() {
        const host = window.location.host === 'localhost:5173' ? 'http://localhost:8080' : window.location.origin
        window.open(host + '/oauth2/authorization/github', '_self')
    }

    const loadUser = () => {
        axios.get('/api/auth/me')
            .then(response => {
                setUserName(response.data);
                setIsLoggedIn(true);
            })
            .catch(() => {
                setUserName("");
                setIsLoggedIn(false);
            })
    }
    const logout = () => {
        const host = window.location.host === 'localhost:5173' ? 'http://localhost:8080' : window.location.origin
        window.open(host + "/api/auth/logout", '_self');
    };
    const handleButtonClick = () => {
        if (isLoggedIn) {
            logout();
        } else {
            login();
        }
    };

    useEffect(() => {
        loadUser()
    }, []);

    return (
        <nav>
            <div>
                Hello {userName}
            </div>
            <button onClick={handleButtonClick}>
                {isLoggedIn ? 'Logout' : 'Login'}
            </button>
        </nav>
    );
}