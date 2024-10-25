import {Route, Routes} from "react-router-dom";
import Main from "./App/Main.tsx";
import Details from "./App/Details.tsx";
import Header from "./App/Header.tsx";
import Footer from "./App/Footer.tsx";
import {useState} from "react";

function App() {
    const [userName, setUserName] = useState('');
    return (
        <>
            <Header userName={userName} setUserName={setUserName}/>
            <Routes>
                <Route path="/" element={<Main userName={userName}/>}/>
                <Route path="api/movie/:id" element={<Details userName={userName}/>}/>
            </Routes>
            <Footer/>
        </>
    )
}

export default App
