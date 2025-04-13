create table tbl_ap(
    id UInt8,
    manufacturer String,
    productClass String,
    serialNumber String,
    pppoeAccount String,
    controllerSerialNumber String
) engine = MergeTree()
order by (id)