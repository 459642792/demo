//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.TimeUnit;
//
//import javax.xml.parsers.ParserConfigurationException;
//import javax.xml.parsers.SAXParser;
//import javax.xml.parsers.SAXParserFactory;
//
//import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
//import org.apache.poi.openxml4j.opc.OPCPackage;
//import org.apache.poi.openxml4j.opc.PackageAccess;
//import org.apache.poi.ss.usermodel.BuiltinFormats;
//import org.apache.poi.ss.usermodel.DataFormatter;
//import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
//import org.apache.poi.xssf.eventusermodel.XSSFReader;
//import org.apache.poi.xssf.model.StylesTable;
//import org.apache.poi.xssf.usermodel.XSSFCellStyle;
//import org.apache.poi.xssf.usermodel.XSSFRichTextString;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.xml.sax.Attributes;
//import org.xml.sax.ContentHandler;
//import org.xml.sax.InputSource;
//import org.xml.sax.SAXException;
//import org.xml.sax.XMLReader;
//import org.xml.sax.helpers.DefaultHandler;
//
//
//
//
//public class ExcelPase {
//    private static final Logger logger = LoggerFactory.getLogger(ExcelPase.class);
//
//
//    /**
//     * The type of the data value is indicated by an attribute on the cell.
//     * The value is usually in a "v" element within the cell.
//     */
//    enum xssfDataType {
//        BOOL,
//        ERROR,
//        FORMULA,
//        INLINESTR,
//        SSTINDEX,
//        NUMBER,
//    }
//    class CommonException extends RuntimeException {
//
//        /**
//         *
//         */
//        private static final long serialVersionUID = 1L;
//
//        private String code;
//
//        private String desc;
//
//        public CommonException(String code, String desc) {
//            this.code = code;
//            this.desc = desc;
//        }
//
//
//        public String getCode() {
//            return code;
//        }
//
//        public void setCode(String code) {
//            this.code = code;
//        }
//
//        public String getDesc() {
//            return desc;
//        }
//
//        public void setDesc(String desc) {
//            this.desc = desc;
//        }
//
//        @Override
//        public String getMessage() {
//            return desc;
//        }
//
//    }
//
//
//    class MyXSSFSheetHandler extends DefaultHandler {
//
//        /**
//         * Table with styles
//         */
//        private StylesTable stylesTable;
//
//        /**
//         * Table with unique strings
//         */
//        private ReadOnlySharedStringsTable sharedStringsTable;
//
//        /**
//         * Destination for data
//         */
//        private final ExcelDealAction action;
//
//        /**
//         * Number of columns to read starting with leftmost
//         */
//        private final int minColumnCount;
//
//        // Set when V start element is seen
//        private boolean vIsOpen;
//
//        // Set when cell start element is seen;
//        // used when cell close element is seen.
//        private xssfDataType nextDataType;
//
//        // Used to format numeric cell values.
//        private short formatIndex;
//        private String formatString;
//        private final DataFormatter formatter;
//
//        private int thisColumn = -1;
//        // The last column printed to the output stream
//        private int lastColumnNumber = -1;
//
//        // Gathers characters as they are seen.
//        private StringBuffer value;
//
//        private Integer dealSize;
//
//        private String[] row;
//        private List<String[]> data;
//        //线程池
//        private ExecutorService fixedThreadPool ;
//        //是否只取第一行
//        private boolean isFirstRow;
//
//        private int beginSize=0;
//
//        public MyXSSFSheetHandler(
//                StylesTable styles,
//                ReadOnlySharedStringsTable strings,
//                int cols,
//                ExcelDealAction action,
//                Integer dealSize,boolean isFirstRow) {
//            this.stylesTable = styles;
//            this.sharedStringsTable = strings;
//            this.minColumnCount = cols;
//            this.action = action;
//            this.value = new StringBuffer();
//            this.nextDataType = xssfDataType.NUMBER;
//            this.formatter = new DataFormatter();
//            this.dealSize=dealSize;
//            this.isFirstRow=isFirstRow;
//            this.row=new String[cols];
//            data=new ArrayList<String[]>();
//            int threadno=isFirstRow?1:Runtime.getRuntime().availableProcessors();
//            fixedThreadPool=Executors.newFixedThreadPool(threadno);
//            logger.info("线程池的初始化数据为:",threadno);
//            beginSize=0;
//        }
//
//        /*
//         * (non-Javadoc)
//         * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
//         */
//        public void startElement(String uri, String localName, String name,
//                                 Attributes attributes) throws SAXException {
//
//            if ("inlineStr".equals(name) || "v".equals(name)) {
//                vIsOpen = true;
//                // Clear contents cache
//                value.setLength(0);
//            }
//            // c => cell
//            else if ("c".equals(name)) {
//                // Get the cell reference
//                String r = attributes.getValue("r");
//                int firstDigit = -1;
//                for (int c = 0; c < r.length(); ++c) {
//                    if (Character.isDigit(r.charAt(c))) {
//                        firstDigit = c;
//                        break;
//                    }
//                }
//                thisColumn = nameToColumn(r.substring(0, firstDigit));
//
//                // Set up defaults.
//                this.nextDataType = xssfDataType.NUMBER;
//                this.formatIndex = -1;
//                this.formatString = null;
//                String cellType = attributes.getValue("t");
//                String cellStyleStr = attributes.getValue("s");
//                if ("b".equals(cellType))
//                    nextDataType = xssfDataType.BOOL;
//                else if ("e".equals(cellType))
//                    nextDataType = xssfDataType.ERROR;
//                else if ("inlineStr".equals(cellType))
//                    nextDataType = xssfDataType.INLINESTR;
//                else if ("s".equals(cellType))
//                    nextDataType = xssfDataType.SSTINDEX;
//                else if ("str".equals(cellType))
//                    nextDataType = xssfDataType.FORMULA;
//                else if (cellStyleStr != null) {
//                    // It's a number, but almost certainly one
//                    //  with a special style or format
//                    int styleIndex = Integer.parseInt(cellStyleStr);
//                    XSSFCellStyle style = stylesTable.getStyleAt(styleIndex);
//                    this.formatIndex = style.getDataFormat();
//                    this.formatString = style.getDataFormatString();
//                    if (this.formatString == null)
//                        this.formatString = BuiltinFormats.getBuiltinFormat(this.formatIndex);
//                }
//            }
//
//        }
//
//        /*
//         * (non-Javadoc)
//         * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
//         */
//        public void endElement(String uri, String localName, String name)
//                throws SAXException {
//
//            String thisStr = null;
//
//            // v => contents of a cell
//            if ("v".equals(name)) {
//                // Process the value contents as required.
//                // Do now, as characters() may be called more than once
//                switch (nextDataType) {
//
//                    case BOOL:
//                        char first = value.charAt(0);
//                        thisStr = first == '0' ? "FALSE" : "TRUE";
//                        break;
//
//                    case ERROR:
//                        thisStr = "\"ERROR:" + value.toString() + '"';
//                        break;
//
//                    case FORMULA:
//                        // A formula could result in a string value,
//                        // so always add double-quote characters.
//                        thisStr = '"' + value.toString() + '"';
//                        break;
//
//                    case INLINESTR:
//                        // TODO: have seen an example of this, so it's untested.
//                        XSSFRichTextString rtsi = new XSSFRichTextString(value.toString());
//                        thisStr = '"' + rtsi.toString() + '"';
//                        break;
//
//                    case SSTINDEX:
//                        String sstIndex = value.toString();
//                        try {
//                            int idx = Integer.parseInt(sstIndex);
//                            XSSFRichTextString rtss = new XSSFRichTextString(sharedStringsTable.getEntryAt(idx));
//                            thisStr = '"' + rtss.toString() + '"';
//                        }
//                        catch (NumberFormatException ex) {
////                            output.println("Failed to parse SST index '" + sstIndex + "': " + ex.toString());
//                        }
//                        break;
//
//                    case NUMBER:
//                        String n = value.toString();
//                        if (this.formatString != null)
//                            thisStr = formatter.formatRawCellContents(Double.parseDouble(n), this.formatIndex, this.formatString);
//                        else
//                            thisStr = n;
//                        break;
//
//                    default:
//                        thisStr = "(TODO: Unexpected type: " + nextDataType + ")";
//                        break;
//                }
//
//                // Output after we've seen the string contents
//                // Emit commas for any fields that were missing on this row
//                if (lastColumnNumber == -1) {
//                    lastColumnNumber = 0;
//                }
//                for (int i = lastColumnNumber; i <= thisColumn; ++i)
//                    row[thisColumn]=thisStr;
//                // Update column
//                if (thisColumn > -1)
//                    lastColumnNumber = thisColumn;
//
//            } else if ("row".equals(name)) {
//
//                // Print out any missing commas if needed
//                if (minColumns > 0) {
//                    // Columns are 0 based
//                    if (lastColumnNumber == -1) {
//                        lastColumnNumber = 0;
//                    }
//                    for (int i = lastColumnNumber; i < (this.minColumnCount); i++) {
////                        output.print(',');
//                        this.data.add(row);
//                        this.row=new String[this.minColumnCount];
//                        if(this.isFirstRow){
//                            this.action.dealList(data,0);
//                            /**
//                             * 需要自己定义的一个异常来中断读取
//                             */
//                            throw new CommonException("", "已取出第一行,中断线程");
//                        }else{
//                            if(this.data.size()==this.dealSize){
//                                /**
//                                 * 用线程来执行
//                                 */
//                                fixedThreadPool.execute(new dealActionThread(this.data,this.beginSize, action));
//                                this.beginSize+=this.data.size();
//                                this.data=new ArrayList<String[]>();
//                            }
//                        }
//                    }
//                }
//
//                // We're onto a new row
////                output.println();
//                lastColumnNumber = -1;
//            }
//
//        }
//
//
//        @Override
//        public void endDocument() throws SAXException {
//            super.endDocument();
//            this.action.dealList(data,this.beginSize);
//            fixedThreadPool.shutdown();
//            try {
//                fixedThreadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//        /**
//         * Captures characters only if a suitable element is open.
//         * Originally was just "v"; extended for inlineStr also.
//         */
//        public void characters(char[] ch, int start, int length)
//                throws SAXException {
//            if (vIsOpen)
//                value.append(ch, start, length);
//        }
//
//        /**
//         * Converts an Excel column name like "C" to a zero-based index.
//         *
//         * @param name
//         * @return Index corresponding to the specified name
//         */
//        private int nameToColumn(String name) {
//            int column = -1;
//            for (int i = 0; i < name.length(); ++i) {
//                int c = name.charAt(i);
//                column = (column + 1) * 26 + c - 'A';
//            }
//            return column;
//        }
//
//    }
//
//    class dealActionThread implements Runnable{
//        private List<String[]> dealList;
//        private ExcelDealAction action;
//        private int beginSize;
//
//        public dealActionThread(List<String[]> dealList,int beginSize,ExcelDealAction action){
//            this.dealList=dealList;
//            this.action=action;
//            this.beginSize=beginSize;
//        }
//        @Override
//        public void run() {
//            this.action.dealList(this.dealList,beginSize);
//        }
//
//    }
//
//    ///////////////////////////////////////
//
//    private OPCPackage xlsxPackage;
//    private int minColumns;
//    private ExcelDealAction action;
//    private int dealSize;
//    private boolean isFirst;
//    /**
//     * Creates a new XLSX -> CSV converter
//     *
//     * @param pkg        The XLSX package to process
//     * @param output     The PrintStream to output the CSV to
//     * @param minColumns The minimum number of columns to output, or -1 for no minimum
//     */
//    public CopyOfXLSX2CSV(OPCPackage pkg, ExcelDealAction action, int minColumns,int dealSize,boolean isFirst) {
//        this.xlsxPackage = pkg;
//        this.action = action;
//        this.minColumns = minColumns;
//        this.dealSize=dealSize;
//        this.isFirst=isFirst;
//    }
//
//    /**
//     * Parses and shows the content of one sheet
//     * using the specified styles and shared-strings tables.
//     *
//     * @param styles
//     * @param strings
//     * @param sheetInputStream
//     */
//    public void processSheet(
//            StylesTable styles,
//            ReadOnlySharedStringsTable strings,
//            InputStream sheetInputStream)
//            throws IOException, ParserConfigurationException, SAXException {
//
//        InputSource sheetSource = new InputSource(sheetInputStream);
//        SAXParserFactory saxFactory = SAXParserFactory.newInstance();
//        SAXParser saxParser = saxFactory.newSAXParser();
//        XMLReader sheetParser = saxParser.getXMLReader();
//        ContentHandler handler = new MyXSSFSheetHandler(styles, strings, this.minColumns, this.action,this.dealSize,this.isFirst);
//        sheetParser.setContentHandler(handler);
//        sheetParser.parse(sheetSource);
//    }
//
//    /**
//     * Initiates the processing of the XLS workbook file to CSV.
//     *
//     * @throws IOException
//     * @throws OpenXML4JException
//     * @throws ParserConfigurationException
//     * @throws SAXException
//     */
//    public void process()
//            throws IOException, OpenXML4JException, ParserConfigurationException, SAXException {
//
//        ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(this.xlsxPackage);
//        XSSFReader xssfReader = new XSSFReader(this.xlsxPackage);
//        StylesTable styles = xssfReader.getStylesTable();
//        XSSFReader.SheetIterator iter = (XSSFReader.SheetIterator) xssfReader.getSheetsData();
//        while (iter.hasNext()) {
//            InputStream stream = iter.next();
//            String sheetName = iter.getSheetName();
//            logger.info("处理表单:",sheetName);
//            processSheet(styles, strings, stream);
//            stream.close();
//        }
//    }
//
//    public static void main(String[] args) throws Exception {
//        File xlsxFile = new File("d:/at2.xlsx");
//        //列数
//        int minColumns = 14;
//        //一次处理的数量
//        int dealSize=1000;
//        long begin =System.currentTimeMillis();
//        OPCPackage p = OPCPackage.open(xlsxFile.getPath(), PackageAccess.READ);
//        CopyOfXLSX2CSV xlsx2csv = new CopyOfXLSX2CSV(p, new ExcelDealAction() {
//            @Override
//            public void dealList(List<String[]> data,int beginSize) {
//                System.out.println("***********"+":"+data.size());
//                for(int i=0;i<data.size();i++){
//                    System.out.println(data.get(i)[4]);
//                }
//            }
//        }, minColumns,dealSize,false);//最后一个参数是是否只读取第一行，为了读取字段名行加的
//        xlsx2csv.process();
//        System.out.println("耗时:"+(System.currentTimeMillis()-begin));
//    }
//
//}