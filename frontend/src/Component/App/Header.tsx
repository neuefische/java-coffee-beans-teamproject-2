import axios from "axios";
import {useEffect} from "react";
import {Link, useNavigate} from "react-router-dom";

export default function Header(
    {userName, setUserName}:
        {
            userName: string,
            setUserName: (name: string) => void
        }
) {
    const navigate = useNavigate();

    function login() {
        const host = window.location.host === 'localhost:5173' ? 'http://localhost:8080' : window.location.origin
        window.open(host + '/oauth2/authorization/github', '_self')
        navigate("/")
    }

    const loadUser = () => {
        axios.get('/api/auth/me')
            .then(response => {
                setUserName(response.data);
            })
            .catch(() => {
                setUserName("");
            })
    }

    const logout = () => {
        axios.post(`/api/auth/logout`, {}).then(() => {
            setUserName("");
            navigate("/");
        })
    };
    const handleButtonClick = () => {
        if (userName) {
            logout();
        } else {
            login();
        }
    };

    useEffect(() => {
        loadUser()
    }, []);

    return (
        <div>
        <nav>
            <Link to="/" className="navbar-title">
                <i className="fa-duotone fa-solid fa-video"></i>
                <h1>Movie Library</h1>
            </Link>
            <div className={"navbar-user"}>
                <p>Hello, {userName}</p>
                <button onClick={handleButtonClick}>
                    {userName ? 'Logout' : 'Login'}
                </button>
            </div>
        </nav>
            <hr></hr>
        </div>
    );
}