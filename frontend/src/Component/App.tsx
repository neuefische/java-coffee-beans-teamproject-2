import {Route, Routes} from "react-router-dom";
import Main from "./App/Main.tsx";
import Details from "./App/Details.tsx";
import Header from "./App/Header.tsx";
import Footer from "./App/Footer.tsx";

function App() {

    return (
        <>
            <Header />
            <Routes>
                <Route path="/" element={<Main />}/>
                <Route path="/movie/:id" element={<Details />}/>
            </Routes>
            <Footer />
        </>
    )
}

export default App
