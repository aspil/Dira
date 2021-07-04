import { Link, useParams } from "react-router-dom";
import arrows from "../Images/arrows.png"

const SideNav = () => {
  const { projectId } = useParams();

  const links = [
    {
      name: "Active Sprint",
      path: `/project/${projectId}/active_sprint`
    },
    {
      name: "Backlog",
      path: `/project/${projectId}/backlog`
    },
    {
      name: "Members",
      path: `/project/${projectId}/members`
    },
  ]

  return (
    <nav className="sidebar">
      <ul >
        {links.map((item) => (
          <li key={item.name}><Link to={item.path} style={{ paddingLeft: "40px" }}>{item.name}</Link></li>
        ))}
      </ul>
      <img src={arrows} alt="arrows" id="arrows" />
    </nav>
  );
}

export default SideNav;