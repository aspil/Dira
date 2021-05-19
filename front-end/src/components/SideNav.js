import { ChevronLeft, ChevronRight } from "@material-ui/icons"
import { useState } from "react";

const SideNav = () => {
  const [shown, setShown] = useState(true);

  const handleSlide = () => {
    const nav = document.getElementsByClassName("side-nav")[0];
    const main = document.getElementsByClassName("content")[0];
    const button = document.getElementsByClassName("slider")[0];

    if(shown) {
      nav.style.animationName = "slide-left";
      main.style.animationName = "full-wd";
      button.innerHTML = '<svg class="MuiSvgIcon-root MuiSvgIcon-fontSizeLarge" focusable="false" viewBox="0 0 24 24" aria-hidden="true"><path d="M10 6L8.59 7.41 13.17 12l-4.58 4.59L10 18l6-6z"></path></svg>'
    }else {
      nav.style.animationName = "slide-right";
      main.style.animationName = "shrink";
      button.innerHTML = '<svg class="MuiSvgIcon-root MuiSvgIcon-fontSizeLarge" focusable="false" viewBox="0 0 24 24" aria-hidden="true"><path d="M15.41 7.41L14 6l-6 6 6 6 1.41-1.41L10.83 12z"></path></svg>'
    }

    setShown(!shown);
  }

  return (
    <div className="side-nav">
      <button
        className="slider"
        onClick={handleSlide}
      >
        <ChevronLeft fontSize="large"/>
      </button>
      nav
    </div>
  );
}
 
export default SideNav;