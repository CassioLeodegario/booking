import { BrowserRouter, Route, Routes } from "react-router-dom"
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import Navbar from "./components/Navbar/Navbar"
import Footer from "./components/Footer/Footer"
import Details from "./components/Details/Details"
import App from "./App"
import BookingList from "./components/BookingList/BookingList";
import { useState } from "react";

const AppRoutes = () => {
    const [transparent, setTransparent] = useState('header');

    
    return (
        <BrowserRouter>
            <Navbar transparent={transparent}/>
            <Routes>
                <Route path='/' element={<App setTransparent={setTransparent} />} />
                <Route path='/details/:id' element={<Details setTransparent={setTransparent} />} />
                <Route path='/list' element={<BookingList setTransparent={setTransparent} />} />
            </Routes>
            <Footer />
            <ToastContainer />
        </BrowserRouter>
    )
}

export default AppRoutes;