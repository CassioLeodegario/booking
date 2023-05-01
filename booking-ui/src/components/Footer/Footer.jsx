import React, { useEffect } from 'react';
import { MdOutlinePlace } from 'react-icons/md'
import { ImFacebook } from 'react-icons/im'
import { BsTwitter } from 'react-icons/bs'
import { AiFillInstagram } from 'react-icons/ai'


import './footer.css';

const Footer = () => {

    return (
        <div className="footer">
            <div className="secContainer container grid">
                <div className="logoDiv">
                    <div className="footerLogo">
                        <a href='#' className='logo flex'>
                            <h1 className='flex'>
                                <MdOutlinePlace className="icon" />
                                FullyBooked
                            </h1>
                        </a>
                    </div>

                    <div className="socials flex">
                        <ImFacebook className="icon" />
                        <BsTwitter className="icon" />
                        <AiFillInstagram className="icon" />
                    </div>
                </div>
                <div className="footerLinks">
                    <span className='linkTitle'>Helpful Links</span>

                    <li>
                        <a href='#'>Destination</a>
                    </li>
                    <li>
                        <a href='#'>Support</a>
                    </li>
                    <li>
                        <a href='#'>Travel and conditions</a>
                    </li>
                    <li>
                        <a href='#'>Privacy</a>
                    </li>
                </div>

                <div className="footerLinks">
                    <span className='linkTitle'>Information</span>

                    <li>
                        <a href='#'>Home</a>
                    </li>
                    <li>
                        <a href='#'>Explore</a>
                    </li>
                    <li>
                        <a href='#'>Travel</a>
                    </li>
                    <li>
                        <a href='#'>Blog</a>
                    </li>
                </div>

                <div className="footerLinks">
                    <span className='linkTitle'>Contact us</span>

                    <span className='phone'>+55 67 99266-0562</span> 
                    <span className='email'>cassio.leodegario@gmail.com</span> 
                </div>
            </div>
        </div>
    )
}

export default Footer;