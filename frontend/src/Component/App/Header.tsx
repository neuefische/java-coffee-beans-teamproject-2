import axios from "axios";
import {useEffect, useState} from "react";
import {Link, useNavigate} from "react-router-dom";

export default function Header() {
    const navigate = useNavigate();

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
        axios.post(`/api/auth/logout`, {}).then(() => {
            navigate("/")
        })};
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
                <Link to="/" className="navbar-title">
                    <i className="fa-duotone fa-solid fa-video"></i>
                    <h1>Movie Library</h1>
                </Link>
                <div className={"navbar-user"}>
                    <p>Hello {userName}</p>
                    <button onClick={handleButtonClick}>
                        {isLoggedIn ? 'Logout' : 'Login'}
                    </button>
                </div>
            </nav>
        );
    }