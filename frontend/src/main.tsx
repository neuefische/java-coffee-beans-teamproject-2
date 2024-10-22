import {StrictMode} from 'react'
import {createRoot} from 'react-dom/client'
import App from './Component/App.tsx'
import './index.css'
import {BrowserRouter} from "react-router-dom";

createRoot(document.getElementById('root')!).render(
    <StrictMode>
        <BrowserRouter>
            <App/>
        </BrowserRouter>
    </StrictMode>,
)
