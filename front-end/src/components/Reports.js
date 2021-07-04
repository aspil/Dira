import SideNav from './SideNav';
import { useEffect, useRef, useState } from 'react'
import { useParams } from 'react-router';
import { Bar } from 'react-chartjs-2';

const Reports = ({ footerHandle, token }) => {
  useEffect(footerHandle, [footerHandle]);
  const { projectId } = useParams();
  const [datasetBarChart, setDatasetBarChart] = useState([]);
  const [dataBarChart, setDataBarChart] = useState(null);

  const fetchBarChartDataset = () => {
    setDatasetBarChart([
      {
        assignee: 'Tester',
        epicsAssigned: 10,
        storiesAssigned: 5,
        defectsAssigned: 19,
      },
      {
        assignee: 'Tester2',
        epicsAssigned: 6,
        storiesAssigned: 15,
        defectsAssigned: 12,
      },
      {
        assignee: 'Tester3',
        epicsAssigned: 16,
        storiesAssigned: 2,
        defectsAssigned: 8,
      },
    ]);
  };
  useEffect(fetchBarChartDataset, []);

  const prepareBarChartProps = () => {
    setDataBarChart({
      labels: datasetBarChart.map(record => record.assignee),
      datasets: [
        {
          label: 'Epics',
          data: datasetBarChart.map(record => record.epicsAssigned),
          backgroundColor: 'rgb(255, 99, 132)',
        },
        {
          label: 'Stories',
          data: datasetBarChart.map(record => record.storiesAssigned),
          backgroundColor: 'rgb(54, 162, 235)',
        },
        {
          label: 'Defects',
          data: datasetBarChart.map(record => record.defectsAssigned),
          backgroundColor: 'rgb(75, 192, 192)',
        },
      ]
    });

  }
  useEffect(prepareBarChartProps, [datasetBarChart]);


  const optionsBarChart = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: {
        display: true,
        position: 'top',
      },
      title: {
        display: true,
        text: 'Issues Assigned per Member'
      },
      scales: {
        yAxes: [
          {
            ticks: {
              beginAtZero: true,
            },
          },
        ],
      },
    }
  };

  return (
    <div className="proj_page">
      <div className="center_content">
        <SideNav />
        <main >
          <div
            style={{
              display: 'flex',
              height: '100%',
              width: '95%',
              margin: 'auto',
              flexDirection: 'column',
              justifyContent: 'space-around'
            }}
          >
            <div style={{ flexBasis: '40%' }}>
              {dataBarChart !== null && <Bar data={dataBarChart} options={optionsBarChart} />}
            </div>

            <div style={{ backgroundColor: 'purple', flexBasis: '40%' }}>
            </div>
          </div>
        </main>
      </div>
    </div>
  );
}

export default Reports;