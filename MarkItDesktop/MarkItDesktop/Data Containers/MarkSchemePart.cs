using System;

namespace MarkItDesktop.Data_Containers
{
    class MarkSchemePart
    {
        public String partAuthor { get; set; }

        public String partName { get; set; }

        public int partAvailableMarks { get; set; }
 
        public int partAssignmentID { get; set; }

        public int partSectionID { get; set; }

        public int partPartID { get; set; }

        public MarkSchemePart()
        {

        }
    }
}
