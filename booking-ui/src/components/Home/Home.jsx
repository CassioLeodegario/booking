import React, { useEffect, useRef } from 'react';
import Aos from 'aos';
import 'aos/dist/aos.css';

import './home.css';
import { useLocation } from 'react-router-dom';

const Home = ({scrollRef, setTransparent}) => {
    const location = useLocation();


    const handleClick = (e) => {
        e.preventDefault();
        scrollRef.current?.scrollIntoView({behavior: 'smooth'});
    };

    useEffect(() => {
        Aos.init({duration: 2000})
    }, [])

    const addBg = () => {
        const pathname = location && location.pathname;
        if (window.scrollY > 10
            || (pathname === '/details')) {
            setTransparent('header activeHeader');
            return;
        }

        setTransparent('header');
    }

    useEffect(() => {
        window.addEventListener('scroll', addBg);
        return () => window.removeEventListener('scroll', addBg);
    }, [])

    return (
        <section className='home'>
            <div className="secContainer container">
                <div className="homeText">
                    <h1 data-aos="fade-up" className="title ">
                        Plan your trip with us
                    </h1>
                    <p data-aos="fade-up" data-aos-duration="2500" className="subtitle">
                        Travel to your favorite city with repect to environment!
                    </p>

                    <button data-aos="fade-up" data-aos-duration="3000" className='btn'>
                        <a href="#" onClick={handleClick}>Explore now</a>
                    </button>

                </div>               
            </div>
        </section >
        
    )
}

export default Home;