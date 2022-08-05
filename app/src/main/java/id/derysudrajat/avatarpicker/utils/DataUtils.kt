package id.derysudrajat.avatarpicker.utils

import id.derysudrajat.avatarpicker.data.CodingWizard
import id.derysudrajat.avatarpicker.data.SampleMenu


val listOfGenre = mutableListOf(
    "Arts & Music",
    "Biographies",
    "Business",
    "Comics",
    "Computers & Tech",
    "Cooking",
    "Edu & Reference",
    "Entertainment",
    "Health & Fitness",
    "History",
    "Hobbies & Crafts",
    "Home & Garden",
    "Horror",
    "Kids",
    "Literature & Fiction",
    "Medical",
    "Mysteries",
    "Parenting",
    "Religion",
    "Romance",
    "Sci-Fi & Fantasy",
    "Action",
    "Science & Math",
    "Self Development",
    "Social Sciences",
    "Sports",
    "Teen",
    "Travel",
    "True Crime"
)

val listOfAvatar = mutableListOf(
    "https://user-images.githubusercontent.com/32610660/183043180-c80aafe0-ba6b-48e7-977f-c05a3773248b.png",
    "https://user-images.githubusercontent.com/32610660/183043194-7e92e6df-65b4-41b7-9f38-ca010088a3ac.png",
    "https://user-images.githubusercontent.com/32610660/183043202-aa41cb9b-4a0e-4809-b877-26c2d34663a4.png",
    "https://user-images.githubusercontent.com/32610660/183043212-c4194804-c81d-44fb-84a2-c469238e4334.png",
    "https://user-images.githubusercontent.com/32610660/183043233-a694b2e2-e892-4db5-bd3c-54db56081330.png",
    "https://user-images.githubusercontent.com/32610660/183043241-b791a9e6-3c45-4234-9083-fb34ad7e6fa9.png",
    "https://user-images.githubusercontent.com/32610660/183050952-51e0f3a4-7521-4840-8c49-ab42b308555c.png",
    "https://user-images.githubusercontent.com/32610660/183050959-1ce02745-9851-46ef-8a37-0b0d792ff634.png",
    "https://user-images.githubusercontent.com/32610660/183050964-08b0fc44-34e2-41dc-875a-030e7522800c.png",
    "https://user-images.githubusercontent.com/32610660/183051472-71d1bad2-2ff7-4452-8205-b8803552d545.png",
    "https://user-images.githubusercontent.com/32610660/183051477-552ce17c-4448-4b7f-a5ad-6364681e7c7a.png",
    "https://user-images.githubusercontent.com/32610660/183051489-92fe42ae-d8e3-4bdc-a0c8-7807a9fcc460.png",
)

val listOfMenu = mutableListOf(
    SampleMenu(
        "https://user-images.githubusercontent.com/32610660/183078449-ba3c429b-4bb7-49d7-aab3-7878fec952b3.png",
        "Tags Picker",
        "Text Drag & Drop"
    ),
    SampleMenu(
        listOfAvatar[3],
        "Avatar Picker",
        "Image Drag & Drop"
    ),
    SampleMenu(
        "https://user-images.githubusercontent.com/32610660/183090744-05d1a3b1-d9c4-41dc-9708-dc3dc3704090.png",
        "Coding Challenge",
        "Custom Object Drag & Drop"
    )
)

val listOfWizard = mutableListOf(
    CodingWizard(listOfAvatar[0], "Marry", 80),
    CodingWizard(listOfAvatar[1], "Azzahra", 65),
    CodingWizard(listOfAvatar[2], "Abraham", 75),
    CodingWizard(listOfAvatar[3], "Dumbledore", 99),
    CodingWizard(listOfAvatar[4], "Ashura", 55),
    CodingWizard(listOfAvatar[5], "Lily", 35),
    CodingWizard(listOfAvatar[6], "Tim", 90),
    CodingWizard(listOfAvatar[7], "Sundar", 88),
    CodingWizard(listOfAvatar[8], "Xenon", 100),
    CodingWizard(listOfAvatar[9], "Nina", 66),
    CodingWizard(listOfAvatar[10], "Mio", 75),
    CodingWizard(listOfAvatar[11], "Lia", 20),
)