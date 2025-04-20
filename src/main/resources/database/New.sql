create table tbl_ap(
    id                          UInt8,
    manufacturer                String,
    product_class               String,
    ap_serial_number            String,
    pppoe_account               String,
    controller_serial_number    String
) engine = MergeTree()
order by (id)