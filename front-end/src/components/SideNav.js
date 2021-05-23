import { Link } from "react-router-dom";
import arrows from "../Images/arrows.png"

const SideNav = (/*{ links }*/) => {
  const links = [
    {
      name: "Active Sprint",
      path: "/"
    },
    {
      name: "Backlog",
      path: "/backlog"
    },
    {
      name: "Epics",
      path: "/"
    },
    {
      name: "Members",
      path: "/members"
    },
  ]

  return (
    <nav className="sidebar">
      <ul>
        {links.map((item) => (
          <li><Link to={item.path}>{item.name}</Link></li>
        ))}
      </ul>
      <img src={arrows} alt="arrows" id="arrows"/>
    </nav>
  );
}
 
export default SideNav;