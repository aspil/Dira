import SideNav from './SideNav';
import ProjectNav from './ProjectNav';
import Footer from './Footer';
import { useState } from "react";
import { PlayForWorkOutlined } from '@material-ui/icons';

const Epics = () => {


    return (
        <div className="Epics">
            <ProjectNav/>

            <SideNav/>

            <div style={{clear: "both", position:"absolute", bottom:"0", width:"100%"}}>
                <Footer/>
            </div>
        </div>

    );
}
        
    export default Epics;