# Create EFS Drive
1. On Console Choose EFS under storage
2. Click on Create Filesystem
3. Give name and VPC (default)

## Customize, you can choose from
- Automatic backup
- lifecycle management
 - moving items after certain days to infrequently accessed storage
- performance mode 
 - General purpose
 - Max I/O
- Throughput mode
 - Bursting
 - Provisioned
- Encryption

The mount targets are created in every az and default subnet of that vpc giving default security group

## Attach
1. install mount helper 
 - sudo yum install -y amazon-efs-utils (please note you route table has entry for internet)
2. create drive to mount on  
 > sudo mkdir -p /mnt/efs
3. execute mount command 
 - sudo mount -t efs -o tls fs-12345678:/ /mnt/efs
4. Give permissions to read/write files in this or any sub directory