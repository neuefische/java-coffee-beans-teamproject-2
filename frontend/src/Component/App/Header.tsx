import axios from "axios";
import {useEffect, useState} from "react";

export default function Header() {
    function login() {

        const host = window.location.host === 'localhost:5173' ? 'http://localhost:8080' : window.location.origin

        window.open(host + '/oauth2/authorization/github', '_self')
    }
    const loadUser = () => {
        axios.get('/api/auth/me')
            .then(response => {
                console.log(response.data)
                setUserName(response.data)
            })
    }

    useEffect(() => {
        loadUser()
    }, []);

    const [userName, setUserName] = useState('')
    return (
        <nav>
            <div>
                Hello {userName}
            </div>
            <button onClick={login}>
                Login
            </button>
        </nav>
    );
}