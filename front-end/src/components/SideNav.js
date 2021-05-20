import { ChevronLeft, ChevronRight } from "@material-ui/icons"
import { useState } from "react";
import { Link } from "react-router-dom";

const SideNav = (/*{ links }*/) => {
  const links = [
    {
      name: "Backlog",
      path: "/backlog"
    },
    {
      name: "Active Sprint",
      path: "/"
    },
    {
      name: "Epics",
      path: "/"
    },
  ]

  return (
    <nav>
      <ul>
        {links.map((item) => (
          <li><Link to={item.path}>{item.name}</Link></li>
        ))}
      </ul>
    </nav>
  );
}
 
export default SideNav;