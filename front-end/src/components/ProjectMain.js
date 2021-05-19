import ProjectNav from './ProjectNav';
import SideNav from './SideNav';
// import logo from "../Images/dira_icon.png"

function Home() {
  return (
    <div className="projectmain">
      <ProjectNav/>
      <div className="center">
        <SideNav/>
        <div className="content">
          <p>
            Lorem ipsum dolor sit amet consectetur adipisicing elit. Hic incidunt blanditiis, esse sequi nulla dicta et veniam tenetur corrupti earum ducimus vel, similique expedita sit voluptatum accusantium minus ex aliquid!
          </p>
        </div>
      </div>

    </div>
  );
}
 
export default Home;

// const Home = () => {
//   return (
//     <div>
//       Home page
//     </div>
//   );
// }
 
// export default Home;