import { AiFillCloseCircle } from 'react-icons/ai';
import React, { useEffect, useState } from 'react';
import { MdOutlinePlace } from "react-icons/md";
import { TbGridDots } from "react-icons/tb";

import './navbar.css';
import { useNavigate } from 'react-router-dom';

const Navbar = ({ transparent }) => {
    const [active, setActive] = useState('navbar');
    const navigate = useNavigate()

    const showNavbar = () => {
        setActive('navbar activeNavbar');
    }

    const closeNavbar = () => {
        setActive('navbar');
    }

    const handleMyBookingsClick = (e) => {
        e.preventDefault()
        navigate('/list')
    }

    const handleHomeClick = (e) => {
        e.preventDefault()
        navigate('/')
    }

    return (
        <section className='navbarSection'>
            <div className={transparent}>
                <div className='logoDiv'>
                    <a href='#' className='logo'>
                        <h1><MdOutlinePlace className="icon" />
                            FullyBooked
                        </h1>
                    </a>
                </div>

                <div className={active}>
                    <ul className='navList flex'>

                        <div className="headerBtns flex">
                            <button className='btn homeBtn' onClick={handleHomeClick}><a href="#">Home</a></button>
                            <button className='btn' onClick={handleMyBookingsClick}><a href="#">My Bookings</a></button>

                        </div>

                        <div onClick={closeNavbar} className="closeNavbar">
                            <AiFillCloseCircle className="icon" />
                        </div>
                    </ul>
                </div>

                <div onClick={showNavbar} className="toggleNavbar">
                    <TbGridDots className="icon" />
                </div>
            </div>
        </section>
    )
}

export default Navbar;