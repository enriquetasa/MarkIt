using MarkItDesktop.Data_Containers;
using System;
using System.Collections.Generic;
using System.Xml;

namespace MarkItDesktop
{
    // This whole class is a utility to read the specific XML files created as templates. The files can be modified by the user and fed into this app which will read through this class and upload to the cloud
    class XMLReader
    {
        public bool GetMarkSchemeInfo(String pathToXML, ref MarkScheme inputMarkScheme)
        {
            try
            {
                XmlDocument doc = new XmlDocument();
                doc.Load(pathToXML);

                // Finds XML tags with that name
                XmlNodeList elemList = doc.GetElementsByTagName("assignmentName");
                for (int i = 0; i < elemList.Count; i++)
                {
                    // Gets inner text and saves it into programmatical data structure for later use
                    inputMarkScheme.markSchemeAssignmentName = elemList[i].InnerText;
                }
                XmlNodeList elemList1 = doc.GetElementsByTagName("assignmentNumber");
                for (int i = 0; i < elemList1.Count; i++)
                {
                    inputMarkScheme.markSchemeAssignmentID = int.Parse(elemList1[i].InnerText);
                }
                XmlNodeList elemList2 = doc.GetElementsByTagName("assignmentAvailableMarks");
                for (int i = 0; i < elemList2.Count; i++)
                {
                    inputMarkScheme.markSchemeAssignmentAvailableMarks = int.Parse(elemList2[i].InnerText);
                }
                XmlNodeList elemList4 = doc.GetElementsByTagName("assignmentAuthor");
                for (int i = 0; i < elemList4.Count; i++)
                {
                    inputMarkScheme.markSchemeAssignmentAuthor = elemList4[i].InnerText;
                }
                XmlNodeList elemList5 = doc.GetElementsByTagName("assignmentLabGroup");
                for (int i = 0; i < elemList4.Count; i++)
                {
                    inputMarkScheme.markSchemeAssignmentAuthor = elemList4[i].InnerText;
                }

                return true;
            }
            catch (Exception e)
            {
                return false;
            }
        } // Reads XML and extracts Mark Scheme info into a data structure passed by reference

        public bool GetLabGroupInfo(String pathToXML, ref LabGroup inputLabGroup)
        {
            try
            {
                XmlDocument doc = new XmlDocument();
                doc.Load(pathToXML);

                XmlNodeList elemList = doc.GetElementsByTagName("LabGroupLocation");
                for (int i = 0; i < elemList.Count; i++)
                {
                    inputLabGroup.labGroupLocation = elemList[i].InnerText;
                }
                XmlNodeList elemList1 = doc.GetElementsByTagName("LabGroupNumber");
                for (int i = 0; i < elemList1.Count; i++)
                {
                    inputLabGroup.labGroupNumber = int.Parse(elemList1[i].InnerText);
                }
                XmlNodeList elemList2 = doc.GetElementsByTagName("LabGroupStudentID");
                for (int i = 0; i < elemList2.Count; i++)
                {
                    inputLabGroup.labGroupStudentID = int.Parse(elemList2[i].InnerText);
                }
                XmlNodeList elemList4 = doc.GetElementsByTagName("LabGroupUnit");
                for (int i = 0; i < elemList4.Count; i++)
                {
                    inputLabGroup.labGroupUnit = elemList4[i].InnerText;
                }
                XmlNodeList elemList5 = doc.GetElementsByTagName("assignmentNumber");
                for (int i = 0; i < elemList4.Count; i++)
                {
                    inputLabGroup.labGroupAssignmentID = int.Parse(elemList5[i].InnerText);
                }

                return true;
            }
            catch (Exception e)
            {
                return false;
            }
        } // Reads XML and extracts Lab Group info into a data structure passed by reference

        public bool GetMarkSchemeSectionsInfo(String pathToXML, int assingmentID, ref List<MarkSchemeSection> inputMarkSchemeSectionList)
        {
            try
            {
                XmlDocument doc = new XmlDocument();
                doc.Load(pathToXML);

                XmlNodeList elemList = doc.GetElementsByTagName("section");
                for (int i = 0; i < elemList.Count; i++)
                {
                    inputMarkSchemeSectionList.Add(new MarkSchemeSection());
                }

                for (int i = 0; i < elemList.Count; i++)
                {
                    inputMarkSchemeSectionList[i].sectionAssignmentID = assingmentID;

                    XmlNodeList sectionNumber = doc.GetElementsByTagName("sectionNumber");
                    for (int j = 0; j < sectionNumber.Count; j++)
                    {
                        inputMarkSchemeSectionList[j].sectionSectionID = int.Parse(sectionNumber[j].InnerText);
                    }

                    XmlNodeList sectionName = doc.GetElementsByTagName("sectionName");
                    for (int j = 0; j < sectionName.Count; j++)
                    {
                        inputMarkSchemeSectionList[j].sectionName = sectionName[j].InnerText;
                    }

                    XmlNodeList sectionAvailableMarks = doc.GetElementsByTagName("sectionAvailableMarks");
                    for (int j = 0; j < sectionAvailableMarks.Count; j++)
                    {
                        inputMarkSchemeSectionList[j].sectionAvailableMarks = int.Parse(sectionAvailableMarks[j].InnerText);
                    }

                    XmlNodeList sectionAuthor = doc.GetElementsByTagName("sectionAuthor");
                    for (int j = 0; j < sectionAuthor.Count; j++)
                    {
                        inputMarkSchemeSectionList[j].sectionAuthor = sectionAuthor[j].InnerText;
                    }
                }

                return true;
            }
            catch (Exception e)
            {
                return false; 
            }
        } // Reads XML and extracts Mark Scheme Section info into a data structure passed by reference

        public bool GetMarkSchemePartsInfo(String pathToXML, int assingmentID, ref List<MarkSchemePart> inputMarkSchemePartsList)
        {
            try
            {
                XmlDocument doc = new XmlDocument();
                doc.Load(pathToXML);

                XmlNodeList amountOfParts = doc.GetElementsByTagName("part");
                foreach (var part in amountOfParts)
                {
                    inputMarkSchemePartsList.Add(new MarkSchemePart());
                }
                int i = 0;
                int j = 0;
                XmlNodeList sectionList = doc.GetElementsByTagName("section");
                foreach (XmlNode section in sectionList) 
                {
                    XmlNodeList partsWithinSection = section.SelectNodes("part");

                    foreach (XmlNode part in partsWithinSection)
                    {
                        inputMarkSchemePartsList[j].partSectionID = int.Parse(sectionList[i].FirstChild.InnerText);
                        inputMarkSchemePartsList[j].partAssignmentID = assingmentID;

                        XmlNodeList partNumber = doc.GetElementsByTagName("partNumber");
                        for (int partNumberIterator = 0; partNumberIterator < partNumber.Count; partNumberIterator++)
                        {
                            inputMarkSchemePartsList[partNumberIterator].partPartID = int.Parse(partNumber[partNumberIterator].InnerText);
                        }

                        XmlNodeList partName = doc.GetElementsByTagName("partName");
                        for (int partNameIterator = 0; partNameIterator < partName.Count; partNameIterator++)
                        {
                            inputMarkSchemePartsList[partNameIterator].partName = partName[partNameIterator].InnerText;
                        }

                        XmlNodeList partAvailableMarks = doc.GetElementsByTagName("partAvailableMarks");
                        for (int partMarksIterator = 0; partMarksIterator < partAvailableMarks.Count; partMarksIterator++)
                        {
                            inputMarkSchemePartsList[partMarksIterator].partAvailableMarks = int.Parse(partAvailableMarks[partMarksIterator].InnerText);
                        }

                        XmlNodeList partAuthor = doc.GetElementsByTagName("partAuthor");
                        for (int partAuthorIterator = 0; partAuthorIterator < partAuthor.Count; partAuthorIterator++)
                        {
                            inputMarkSchemePartsList[partAuthorIterator].partAuthor = partAuthor[partAuthorIterator].InnerText;
                        }
                        j++;
                    }
                    i++;
                }

                return true;
            }
            catch (Exception e)
            {
                return false;
            }
        } // Reads XML and extracts Mark Scheme Part info into a data structure passed by reference 
    }
}
