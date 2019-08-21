using MarkItDesktop.Data_Containers;
using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Text;

namespace MarkItDesktop
{
    class DatabaseEnquerier
    {
        public List<int> GetAssignmentListFromCloud()
        {
            List<int> assignments = new List<int>();

            try
            {
                // SQL connection info
                SqlConnectionStringBuilder builder = new SqlConnectionStringBuilder();
                builder.DataSource = "tarambana.database.windows.net";
                builder.UserID = "tasa";
                builder.Password = "2019Green!";
                builder.InitialCatalog = "bookChoice";

                using (SqlConnection connection = new SqlConnection(builder.ConnectionString))
                {
                    // Open connection and build query
                    connection.Open();
                    StringBuilder sb = new StringBuilder();
                    sb.Append("SELECT[TotalAssignmentID]");
                    sb.Append("FROM[dbo].[StudentTotalMarks]");
                    String sql = sb.ToString();
                   
                    using (SqlCommand command = new SqlCommand(sql, connection))
                    {
                        // execute query command, take result and read it
                        using (SqlDataReader reader = command.ExecuteReader())
                        {
                            while (reader.Read())
                            {
                                String data = reader["TotalAssignmentID"].ToString();
                                if (!assignments.Contains(int.Parse(data)))
                                {
                                    assignments.Add(int.Parse(data));
                                }
                            }
                        }
                    }
                }
            }
            catch (SqlException e)
            {
                Console.WriteLine(e.ToString());
            }

            return assignments;
        } // returns the list of assignments from the cloud to populate the dropbox

        public Dictionary<int, int> GetStudentGradesFromCloud(int assignmentID)
        {
            Dictionary<int, int> studentIDStudentGrade = new Dictionary<int, int>();

            try
            {
                SqlConnectionStringBuilder builder = new SqlConnectionStringBuilder();
                builder.DataSource = "tarambana.database.windows.net";
                builder.UserID = "tasa";
                builder.Password = "2019Green!";
                builder.InitialCatalog = "bookChoice";

                using (SqlConnection connection = new SqlConnection(builder.ConnectionString))
                {
                    connection.Open();
                    StringBuilder sb = new StringBuilder();
                    sb.Append("SELECT [TotalMarksAchieved], [TotalStudentID]");
                    sb.Append("FROM[dbo].[StudentTotalMarks]");
                    sb.Append("WHERE [TotalAssignmentID] = ");
                    sb.Append(assignmentID);
                    sb.Append("ORDER BY [createdAt] DESC");
                    String sql = sb.ToString();

                    using (SqlCommand command = new SqlCommand(sql, connection))
                    {
                        using (SqlDataReader reader = command.ExecuteReader())
                        {
                            while (reader.Read())
                            {
                                if (!studentIDStudentGrade.ContainsKey(Convert.ToInt32(reader["TotalStudentID"])))
                                    {
                                    studentIDStudentGrade.Add(Convert.ToInt32(reader["TotalStudentID"]),
                                        Convert.ToInt32(reader["TotalMarksAchieved"]));
                                    }
                            }
                        }
                    }
                }
            }
            catch (SqlException e)
            {
                Console.WriteLine(e.ToString());
            }

            return studentIDStudentGrade;
        } // returns studentID-studentGrade pairs in order to write into CSV file

        public bool UploadMarkScheme(MarkScheme markSchemeToUpload)
        {
            try
            {
                SqlConnectionStringBuilder builder = new SqlConnectionStringBuilder();
                builder.DataSource = "tarambana.database.windows.net";
                builder.UserID = "tasa";
                builder.Password = "2019Green!";
                builder.InitialCatalog = "bookChoice";

                using (SqlConnection connection = new SqlConnection(builder.ConnectionString))
                {
                    using (SqlCommand command = new SqlCommand())
                    {
                        command.Connection = connection;
                        command.CommandType = CommandType.Text;
                        command.CommandText = "INSERT INTO dbo.MarkScheme (MarkSchemeAssignmentName, MarkSchemeAuthor, MarkSchemeAssignmentAvailableMarks, MarkSchemeAssignmentID) VALUES (@MarkSchemeAssignmentName, @MarkSchemeAuthor, @MarkSchemeAssignmentAvailableMarks, @MarkSchemeAssignmentID)";
                        command.Parameters.AddWithValue("@MarkSchemeAssignmentName", markSchemeToUpload.markSchemeAssignmentName);
                        command.Parameters.AddWithValue("@MarkSchemeAuthor", markSchemeToUpload.markSchemeAssignmentAuthor);
                        command.Parameters.AddWithValue("@MarkSchemeAssignmentAvailableMarks", markSchemeToUpload.markSchemeAssignmentAvailableMarks);
                        command.Parameters.AddWithValue("@MarkSchemeAssignmentID", markSchemeToUpload.markSchemeAssignmentID);

                        connection.Open();
                        command.ExecuteNonQuery();
                        return true;
                    }
                }
            }
            catch (SqlException e)
            {
                Console.WriteLine(e.ToString());
                return false;
            }
        } // SQL insert of the Mark Scheme

        public bool UploadMarkSchemeSections(List<MarkSchemeSection> markSchemeSectionsToUpload)
        {
            try
            {
                SqlConnectionStringBuilder builder = new SqlConnectionStringBuilder();
                builder.DataSource = "tarambana.database.windows.net";
                builder.UserID = "tasa";
                builder.Password = "2019Green!";
                builder.InitialCatalog = "bookChoice";

                foreach (MarkSchemeSection section in markSchemeSectionsToUpload)
                {
                    using (SqlConnection connection = new SqlConnection(builder.ConnectionString))
                    {
                        using (SqlCommand command = new SqlCommand())
                        {

                                command.Connection = connection;
                                command.CommandType = CommandType.Text;
                                command.CommandText = "INSERT INTO dbo.MarkSchemeSection (SectionAuthor, SectionName, SectionAssignmentID, SectionAvailableMarks, SectionID) VALUES (@SectionAuthor, @SectionName, @SectionAssignmentID, @SectionAvailableMarks, @SectionID)";
                                command.Parameters.AddWithValue("@SectionAuthor", section.sectionAuthor);
                                command.Parameters.AddWithValue("@SectionName", section.sectionName);
                                command.Parameters.AddWithValue("@SectionAssignmentID", section.sectionAssignmentID);
                                command.Parameters.AddWithValue("@SectionAvailableMarks", section.sectionAvailableMarks);
                                command.Parameters.AddWithValue("@SectionID", section.sectionSectionID);

                                connection.Open();
                                command.ExecuteNonQuery();
                                connection.Close();
                            }
                        }
                    }
                return true;
            }
            catch (SqlException e)
            {
                Console.WriteLine(e.ToString());
                return false;
            }
        } // SQL insert of the Mark Scheme Sections

        public bool UploadMarkSchemePart(List<MarkSchemePart> markSchemePartsToUpload)
        {
            try
            {
                SqlConnectionStringBuilder builder = new SqlConnectionStringBuilder();
                builder.DataSource = "tarambana.database.windows.net";
                builder.UserID = "tasa";
                builder.Password = "2019Green!";
                builder.InitialCatalog = "bookChoice";

                foreach (MarkSchemePart part in markSchemePartsToUpload)
                {
                  using (SqlConnection connection = new SqlConnection(builder.ConnectionString))
                  {
                      using (SqlCommand command = new SqlCommand())
                      {


                              command.Connection = connection;
                              command.CommandType = CommandType.Text;
                              command.CommandText = "INSERT INTO dbo.MarkSchemePart (PartAuthor, PartName, PartAssignmentID, PartAvailableMarks, PartSectionID, PartPartID) VALUES (@PartAuthor, @PartName, @PartAssignmentID, @PartAvailableMarks, @PartSectionID, @PartPartID)";
                              command.Parameters.AddWithValue("@PartAuthor", part.partAuthor);
                              command.Parameters.AddWithValue("@PartName", part.partName);
                              command.Parameters.AddWithValue("@PartAssignmentID", part.partAssignmentID);
                              command.Parameters.AddWithValue("@PartAvailableMarks", part.partAvailableMarks);
                              command.Parameters.AddWithValue("@PartSectionID", part.partSectionID);
                              command.Parameters.AddWithValue("@PartPartID", part.partPartID);

                              connection.Open();
                              command.ExecuteNonQuery();
                              connection.Close();
                          }
                      }
                  }
                return true;
             }
            catch (SqlException e)
            {
                Console.WriteLine(e.ToString());
                return false;
            }
        } // SQL insert of the Mark Scheme Parts

        public bool UploadLabGroup(LabGroup labGroupToUpload)
        {
            try
            {
                SqlConnectionStringBuilder builder = new SqlConnectionStringBuilder();
                builder.DataSource = "tarambana.database.windows.net";
                builder.UserID = "tasa";
                builder.Password = "2019Green!";
                builder.InitialCatalog = "bookChoice";

                using (SqlConnection connection = new SqlConnection(builder.ConnectionString))
                {
                    using (SqlCommand command = new SqlCommand())
                    {
                        command.Connection = connection;
                        command.CommandType = CommandType.Text;
                        command.CommandText = "INSERT INTO dbo.LabGroup (LabGroupAssignmentID, LabGroupLocation, LabGroupNumber, LabGroupStudentID, LabGroupUnit) VALUES (@labGroupAssignmentID, @labGroupLocation, @labGroupNumber, @labGroupStudentID, @labGroupUnit)";
                        command.Parameters.AddWithValue("@labGroupAssignmentID", labGroupToUpload.labGroupAssignmentID);
                        command.Parameters.AddWithValue("@labGroupLocation", labGroupToUpload.labGroupLocation);
                        command.Parameters.AddWithValue("@labGroupNumber", labGroupToUpload.labGroupNumber);
                        command.Parameters.AddWithValue("@labGroupStudentID", labGroupToUpload.labGroupStudentID);
                        command.Parameters.AddWithValue("@labGroupUnit", labGroupToUpload.labGroupUnit);

                        connection.Open();
                        command.ExecuteNonQuery();
                        return true;
                    }
                }
            }
            catch (SqlException e)
            {
                Console.WriteLine(e.ToString());
                return false;
            } // SQL insert of the Mark Scheme LabGroup
        }
    }
}
