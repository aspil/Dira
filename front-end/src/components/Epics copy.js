import SideNav from './SideNav';
import ProjectNav from './ProjectNav';
import Footer from './Footer';
import { useState } from "react";
import { PlayForWorkOutlined } from '@material-ui/icons';

const Epics = () => {


    return (
        <div className="epics proj_page">
      <ProjectNav />
      <div className="center_content">
        <SideNav />
        <main>

            
        </main>
      </div>
      <Footer />
    </div>
  );
}
        
    export default Epics;