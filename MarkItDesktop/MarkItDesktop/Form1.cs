using MarkItDesktop.Data_Containers;
using System;
using System.Collections.Generic;
using System.IO;
using System.Windows.Forms;

namespace MarkItDesktop
{
    public partial class pathBtn : Form
    {
        DatabaseEnquerier databaseConnection;
        String pathToCSV, pathToXML;
        int assignmentID;

        public pathBtn()
        {

            InitializeComponent();
            DatabaseEnquerier enquerier = new DatabaseEnquerier();
            this.databaseConnection = enquerier;
            RefreshAssignmentComboBox();
        } // On initialisation, set a global communicator with the database and get the combobox populated

        private void RefreshAssignmentComboBox()
        {
            assignmentCB.DataSource = databaseConnection.GetAssignmentListFromCloud();
        }

        private void selectPathBtn_Click(object sender, EventArgs e)
        {
            FolderBrowserDialog dialog = new FolderBrowserDialog();

            if (dialog.ShowDialog() == DialogResult.OK)
            {
                pathToCSV = dialog.SelectedPath;
                downloadLbl.Text = "Download location:\n" + dialog.SelectedPath;
            }
        } // Gets path to download location, sets it in UI

        private void downloadBtn_Click_1(object sender, EventArgs e)
        {
            Dictionary<int, int> studentIDStudentGradeData = databaseConnection.GetStudentGradesFromCloud((int)assignmentCB.SelectedItem);
            var filepath = pathToCSV + "\\downloadedAssigment" + assignmentCB.SelectedItem + ".csv";
            using (StreamWriter writer = new StreamWriter(new FileStream(filepath,
            FileMode.Create, FileAccess.Write)))
            {
                writer.WriteLine("StudentID,StudentMarks");

                foreach (var line in studentIDStudentGradeData)
                {
                    writer.WriteLine(line.Key + "," + line.Value);
                }
            }

            MessageBox.Show("Download complete");
        } // Uses methods from the enquirer class to download the data, writes it to a CSV and saves it in specified location

        private void findXMLBtn_Click(object sender, EventArgs e)
        {
            OpenFileDialog dialog = new OpenFileDialog();

            if (dialog.ShowDialog() == DialogResult.OK)
            {
                pathToXML = dialog.FileName;
                XMLocationLbl.Text = "XML location:\n" + dialog.FileName;
            }
        } // Allows user to select path to XML to upload

        private void uploadBtn_Click(object sender, EventArgs e)
        {
            XMLReader XMLManager = new XMLReader();

            MarkScheme markSchemeInfo = new MarkScheme();
            LabGroup labGroupInfo = new LabGroup();
            List<MarkSchemeSection> markSchemeSectionsInfo = new List<MarkSchemeSection>();
            List<MarkSchemePart> markSchemePartsInfo = new List<MarkSchemePart>();

            if (XMLManager.GetMarkSchemeInfo(pathToXML, ref markSchemeInfo))
            {
                if (XMLManager.GetLabGroupInfo(pathToXML, ref labGroupInfo))
                {
                    assignmentID = markSchemeInfo.markSchemeAssignmentID;

                    if (XMLManager.GetMarkSchemeSectionsInfo(pathToXML, assignmentID, ref markSchemeSectionsInfo))
                    {
                        if (XMLManager.GetMarkSchemePartsInfo(pathToXML, assignmentID, ref markSchemePartsInfo))
                        {

                        }
                        else
                        {
                            MessageBox.Show("Error in reading parts from XML, check file and retry");
                        }
                    }
                    else
                    {
                        MessageBox.Show("Error in sections parts from XML, check file and retry");
                    }
                }
                else
                {
                    MessageBox.Show("Error in reading lab group information from XML, check file and retry");
                }
            }
            else
            {
                MessageBox.Show("Error in reading mark scheme information from XML, check file and retry");
            }

            if (databaseConnection.UploadMarkScheme(markSchemeInfo))
            {
                if (databaseConnection.UploadLabGroup(labGroupInfo))
                {
                    if (databaseConnection.UploadMarkSchemeSections(markSchemeSectionsInfo))
                    {
                        if (databaseConnection.UploadMarkSchemePart(markSchemePartsInfo))
                        {
                        }
                        else
                        {
                            MessageBox.Show("Error in part upload, retry");
                        }
                    }
                    else
                    {
                        MessageBox.Show("Error in section upload, retry");
                    }
                }
                else
                {
                    MessageBox.Show("Error in lab group upload, retry");
                }
            }
            else
            {
                MessageBox.Show("Error in mark scheme upload, retry");
            }

            MessageBox.Show("Upload complete");
        }
    }
} // Takes XML, reads and stores its data using XMLReader methods, uploads that information using enquirer methods. Safety built in via if/else boolean statements