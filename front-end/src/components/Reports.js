import SideNav from './SideNav';
import { useEffect, useRef, useState } from 'react'
import { useParams } from 'react-router';
import { Bar } from 'react-chartjs-2';
import { DiraIssueClient } from 'dira-clients';

const Reports = ({ footerHandle, token, projectClientRef, fetchAllIssues, fetchMembers }) => {
  // const [datasetBarChart, setDatasetBarChart] = useState([]);
  const [dataIssuesByMemberBarChart, setDataIssuesByMemberBarChart] = useState(null);
  const [dataIssuesByPriorityBarChart, setDataIssuesByPriorityBarChart] = useState(null);
  const [projectIssues, setProjectIssues] = useState([]);
  const [projectMembers, setProjectMembers] = useState([]);
  const [projectName, setProjectName] = useState('');

  useEffect(footerHandle, [footerHandle]);

  const { projectId } = useParams();
  const issueClientRef = useRef(new DiraIssueClient(projectId));

  useEffect(() => {
    if (token) {
      issueClientRef.current.set_authorization_token(token);
    }
  }, [token, issueClientRef]);

  useEffect(() => fetchAllIssues(issueClientRef, setProjectIssues, setProjectName, null, null),
    [issueClientRef, issueClientRef.current.headers.Authorization, fetchAllIssues]
  );
  useEffect(() => fetchMembers(projectId, setProjectMembers),
    [projectClientRef, projectId, projectClientRef.current.headers.Authorization, fetchMembers]
  );

  // const fetchBarChartDataset = () => {
  //   setDatasetBarChart([
  //     {
  //       assignee: 'Tester',
  //       epicsAssigned: 10,
  //       storiesAssigned: 5,
  //       defectsAssigned: 19,
  //     },
  //     {
  //       assignee: 'Tester2',
  //       epicsAssigned: 6,
  //       storiesAssigned: 15,
  //       defectsAssigned: 12,
  //     },
  //     {
  //       assignee: 'Tester3',
  //       epicsAssigned: 16,
  //       storiesAssigned: 2,
  //       defectsAssigned: 8,
  //     },
  //   ]);
  // };
  // useEffect(fetchBarChartDataset, []);

  const prepareIssuesByMemberBarChartProps = () => {
    setDataIssuesByMemberBarChart({
      labels: projectMembers.map(member => member.name),
      datasets: [
        {
          label: 'Epics',
          data: projectMembers.map(member => projectIssues.filter(issue => issue.assignee === member.name && issue.type === 'Epic').length),
          backgroundColor: 'rgb(255, 99, 132, 0.4)',
          borderColor: 'rgb(255, 99, 132, 1)',
          borderWidth: 2
        },
        {
          label: 'Stories',
          data: projectMembers.map(member => projectIssues.filter(issue => issue.assignee === member.name && issue.type === 'Story').length),
          backgroundColor: 'rgb(54, 162, 235, 0.4)',
          borderColor: 'rgb(54, 162, 235, 1)',
          borderWidth: 2
        },
        {
          label: 'Defects',
          data: projectMembers.map(member => projectIssues.filter(issue => issue.assignee === member.name && issue.type === 'Defect').length),
          backgroundColor: 'rgb(75, 192, 192, 0.4)',
          borderColor: 'rgb(75, 192, 192, 1)',
          borderWidth: 2
        },
      ]
    });

  }
  useEffect(prepareIssuesByMemberBarChartProps, [projectMembers, projectIssues]);


  const prepareIssuesByPriorityBarChartProps = () => {
    const priorities = [
      'Normal',
      'Low',
      'Major',
      'Blocked'
    ]
    const priorityToColorMapper = {
      Normal: 'rgba(75, 192, 192, 0.4)',
      Low: 'rgba(54, 162, 235, 0.4)',
      Major: 'rgba(255, 99, 132, 0.4)',
      Blocked: 'rgba(153, 102, 255, 0.4)',
    }
    const priorityToColorBorderMapper = {
      Normal: 'rgba(75, 192, 192, 1)',
      Low: 'rgba(54, 162, 235, 1)',
      Major: 'rgba(255, 99, 132, 1)',
      Blocked: 'rgba(153, 102, 255, 1)',
    }
    setDataIssuesByPriorityBarChart({
      labels: priorities,
      datasets: [
        {
          label: '# of Issues',
          data: priorities.map(priority => projectIssues.filter(issue => issue.priority === priority).length),
          backgroundColor: priorities.map(priority => priorityToColorMapper[priority]),
          borderColor: priorities.map(priority => priorityToColorBorderMapper[priority]),
          borderWidth: 2
        }
      ]
    });

  }
  useEffect(prepareIssuesByPriorityBarChartProps, [projectIssues]);

  const getOptionsBarChart = (title, makeHorizontal = false) => {
    return {
      indexAxis: makeHorizontal ? 'y' : 'x',
      responsive: true,
      maintainAspectRatio: false,
      plugins: {
        legend: {
          display: true,
          position: makeHorizontal ? 'right' : 'top'
        },
        title: {
          display: true,
          text: title
        },
        scales: {
          yAxes: !makeHorizontal ? [
            {
              ticks: {
                beginAtZero: true,
              },
            },
          ] : [],
          xAxes: makeHorizontal ? [
            {
              ticks: {
                beginAtZero: true,
              },
            },
          ] : [],
        },
      }
    }
  };

  return (
    <div className="proj_page">
      <div className="center_content">
        <SideNav />
        <main style={{ padding: '0' }}>
          <h1 style={{ textAlign: 'center' }}>{projectName} Statistic Reports</h1>
          <div
            style={{
              display: 'flex',
              height: '100%',
              width: '95%',
              margin: 'auto',
              flexDirection: 'column',
              justifyContent: 'space-evenly'
            }}
          >
            <div style={{ flexBasis: '45%' }}>
              {
                dataIssuesByMemberBarChart !== null
                &&
                <Bar data={dataIssuesByMemberBarChart} options={getOptionsBarChart('Issues Assigned Per Member')} />
              }
            </div>

            <div
              style={{
                flexBasis: '48%',
                marginBottom: '2%',
                display: 'flex',
                justifyContent: 'space-between'
              }}
            >
              <div style={{ flexBasis: '48%' }}>
                {
                  dataIssuesByPriorityBarChart !== null
                  &&
                  <Bar data={dataIssuesByPriorityBarChart} options={getOptionsBarChart('Issues Per Priority', true)} />
                }
              </div>

              <div style={{ flexBasis: '48%' }}>
              </div>
            </div>
          </div>
        </main>
      </div>
    </div>
  );
}

export default Reports;