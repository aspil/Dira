import SideNav from './SideNav';
import { useEffect, useRef, useState } from 'react'
import { useParams } from 'react-router';

const Reports = ({ footerHandle, token }) => {
  useEffect(footerHandle, [footerHandle]);
  const { projectId } = useParams();

  return (
    <div className="proj_page">
      <div className="center_content">
        <SideNav />
        <main>
          <div style={{ display: 'flex', height: '100%', width: '95%', margin: 'auto', flexDirection: 'column', justifyContent: 'space-between' }}>
            <div style={{ backgroundColor: 'hotpink', flexBasis: '48%' }}></div>
            <div style={{ backgroundColor: 'purple', flexBasis: '48%' }}></div>
          </div>
        </main>
      </div>
    </div>
  );
}

export default Reports;