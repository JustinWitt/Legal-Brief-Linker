# Legal-Brief-Linker
A project to add links to pdfs of case law to a legal brief

This was my senior project at Regis University.  
The program takes a Word document and coverts it to a pdf via a VB script.
The program then takes the PDF and identifies cites within the document that are to case law.
It then searches a given directory for pdfs whose titles match that of the case law (specifically the reporter cite).
The program then adds to the cites within the pdf of the brief links to the pages cited in the legal cite.

Or at least that is what it is supposed to do.  There are a lot of issues with it that need to be sorted out regarding the creation of the pdfs of legal cases that are being cited and page numbering. In order for it to work properly the pdfs of the case must have a one-to-one correspondence to the cite. In addition, I need to figure out how to do cites to non-legal sources and "id." cites.
