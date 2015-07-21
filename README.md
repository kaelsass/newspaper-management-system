在去年正式入职之前，经历了一段漫长而煎熬的STM，我的RD4项目是报社管理系统，利用Primefaces框架实现，具体功能自己设计。本文对报社管理系统NP Pillar的功能做一个总结。

# 系统功能
系统功能主要包括8个部分:

- 内容信息管理
- 销售信息管理
- 管理员信息配置
- 员工信息管理
- 时间管理
- 评价系统
- 日程管理
- 个人信息管理
- 权限管理

登陆后页面如下所示：
![登陆页面](http://img.blog.csdn.net/20150720163417447) 
菜单栏对这八部分的功能进行了分类，用户可点击相应的菜单进行操作。

## 内容信息管理
内容信息管理分为5个部分:

- 报纸信息
- 期刊信息
- 文稿信息
- 作者信息
- 主题信息

### 报纸信息页面
报纸信息页面即登陆后的页面，可以根据报纸名、ISSN编号和发行周期进行搜索，报纸信息包括报纸名、发行周期、发行日期、单价和月价，如登陆页面所示。

### 期刊信息页面
In “Issue List” page, user can check and manage all issues information.
![Issue List](http://img.blog.csdn.net/20150720170914562)
1. User can click the small triangle to check and manage all sections belonged to this issue.
2. User can click the pencil button at the end of section information to edit the section. The “Edit Section” page is like following :
![edit page](http://img.blog.csdn.net/20150720171253992)

- User can check section basic information in “Section Info” tab.
- User can arrange page layout in the center panel and save it as image by clicking “Save as Image” button.
- User can check and change design image in the right top panel.
- User can check and manage the articles that contained in this section in “Contained Articles” tab.
- User can review articles when they arrange the section layout.

### 文稿信息页面
User can manage articles information in “Article Info” pages. Only approved articles can be used when editing section.
![Articles info](http://img.blog.csdn.net/20150720171602169)

### 作者信息页面
User can check and manage authors information in “Author Info” pages. Every article must be written by an author.
![Authors info](http://img.blog.csdn.net/20150720171802510)

### 主题信息页面
Every section is marked with a subject. User can check and manage subject information in “Subject” page.

## 销售信息管理
销售信息包括8个部分：

- 订阅订单
- 非订阅订单
- 零售订单
- 广告订单
- 库存信息
- 促销活动信息
- 销售统计信息
- 问卷信息

User can also check inventory information for every issue and sales statistics information based on sales source, client information and promotion activities.

### 订阅订单
User can check and manage subscriber order information in “Subscriber Info” pages. 

### 非订阅订单
User can check and manage non-subscriber order information in “Non-Sub Info” pages. 

### 零售订单
User can check and manage retail order information in “Retail Info” pages. 

> When adding these three kind of orders, user can record how or where the client get the information about the newspaper and client detail information. The system can make sales summary statistics based on these information.
![Clients info](http://img.blog.csdn.net/20150720172804466)

### 广告订单
User can check and manage ad order information in “Ad Info” pages. 

### 库存信息
User can check inventory information including sales quantity for different source in “Inventory Info” page. The issue that has not enough inventory stocks will be highlighted with different color.
![Inventory info](http://img.blog.csdn.net/20150720173006429)

### 促销活动信息
User can check and manage promotion activities information in “Promotion Info” pages. Users can choose “Information Source” from these activities when they create subscriber, non-subscriber and retail orders so the system can make sales summary statistics based on information source to verify their hypnoses.

### 销售统计信息
- User can check sales summary statistics based on sales source in “Sales Source based” page of “Sales Summary” category. User can browse quantity overall statistics, sales trend for newspapers and sales source, sales proportion for different newspapers. 
> You need to choose “From” and “To“ date to check the charts.

![Sales trends](http://img.blog.csdn.net/20150720173408096)

- User can check sales summary statistics based on client information in “Client based” page of “Sales Summary” category. User can check sales quantity statistics based on different age, gender, occupation and education background. Users can make hypnoses about which kind of people are interested which newspaper based on these information.
> You need to choose “From” and “To“ date to check the charts.

![Clients based sales info](http://img.blog.csdn.net/20150720173640042)

- User can check sales summary statistics based on promotion activities in “Promotion based” page of “Sales Summary” category. User can check quantity overall statistics, sales trend, sales proportion for different newspapers. Users can verify their hypnosis based on these information.
> You need to choose “From” and “To“ date to check the charts.

![Promotions based sales info](http://img.blog.csdn.net/20150720173932707)

### 问卷信息
If users still have doubts about the statistics and want to know how clients make their choices, they can create questionnaire to collect thoughts of clients by clicking “Questionnaires” menu in “Questionnaire” category.
![Questionnaire detail](http://img.blog.csdn.net/20150721101259709)

- User can edit questionnaire basic information and add questions for this questionnaire. User can also reorder the questions by clicking “Reorder” button.
- User can preview the questionnaire form when they finish editing basic information and adding questions to the questionnaire. User can also download the questionnaire form so he/she can print the form and distribute leaflets.
![Questionnaire preview](http://img.blog.csdn.net/20150721102058308)

- User can add records for specific questionnaire by selecting “Add Record” action in “Questionnaires” page. 
![Submit questionnaire](http://img.blog.csdn.net/20150721102512347)

- After collecting enough records for a questionnaire, users can check the records and statistics to get thoughts from clients, which will help them to verify their hypnoses.
![Questionnaire statistics](http://img.blog.csdn.net/20150721102707737)
![Questionnaire statistics](http://img.blog.csdn.net/20150721102728364)

## 管理员信息配置
管理员配置信息包括4个部分：

- 管理员账号信息
- 职位相关信息
- 客户信息
- 公司架构

“Admin” consists of user accounts, settings for company management related to Jobs, client and structure.

### 管理员账号信息管理页面
User can check and manage user account information in “User Management” page. 

### 职位相关信息管理页面
User can check and manage job title, employment status, job category information in “Jobs” category. 

### 客户信息管理页面
User can check and manage client information in “Client” category. The items in “Occupation List” and “Education List” can be used to mark client whey he/she puts an order for newspapers. So the system can make statistics based on client information to find potential clients.

### 公司架构管理页面
User can check and manage company structure in “Structure” page. 
![Company Structure](http://img.blog.csdn.net/20150721163645257)

## 员工信息管理
员工信息管理包括3部分功能：

- 员工信息列表
- 添加员工及账号
- 批量导入员工信息

“PIM” consists of employee information management pages.

### 员工信息列表
User can check and manage employee information in “Employee List” page. 
![Employees list](http://img.blog.csdn.net/20150721164114959)
User can export information to different format files by clicking little icons for different format files.
User can check employee photo and basic information by clicking “List|Grid” buttons. 
![Grid view](http://img.blog.csdn.net/20150721164305487)

### 添加员工及账号
User can add employee in “Add Employee” category. 
- User can add login user account for employee at the same time when adding employee information by turn on “Create Login Details” option.
![Add employee](http://img.blog.csdn.net/20150721164459312)

### 批量导入员工信息
User can add upload employee data file to add employees in batch in “Data Import” page. (“uploadTestData.xls” a sample file that you can upload it to finish the adding employee process.)

## 时间管理
时间管理功能包括6个部分：

- 签入签出
- 查看签到信息
- 日工作时间
- 工作时间统计
- 工作计划
- 任务列表

“Time” consists of attendance management pages, task information, and time sheet functions to record employee work time for different tasks.

### 签入签出
User can punch in and punch out in “Punch in/out” page of “Attendance” category. The system will calculate employee work time according to these records.

- User can add punch in record when he/she click “Punch in/out” menu for the first time. The system will record when he/she come to work.
 ![punch in](http://img.blog.csdn.net/20150721165143474)
- User can add punch out record when he/she click “Punch in/out” menu for the second time. The system will record when he/she get off work. User can add multiple punch in/out records for one day in case that they need to get off work in the middle of the day.
![punch out](http://img.blog.csdn.net/20150721165157093)

### 查看签到信息
User can check his/her own punch in/out records for a day in “My Records” page of “Attendance” category.
> You need to choose “Date” to see the records.

![Attendence](http://img.blog.csdn.net/20150721165526510)

### 日工作时间
User can check employee punch in/out records and total work time for a day in “Employee Records” page of “Attendance” category.
> You need to choose “Date“ to see the records.

![Work time](http://img.blog.csdn.net/20150721165643543)

### 工作时间统计
User can check work time summary for a period time in “Attendance Summary” page of “Attendance” category. Users can browse total work time for different employee and work time curve for different day.
> You need to choose “From” and “To” date to see the records.
> There are sample data from 2014-11-01 to 2014-11-05

![Work time list](http://img.blog.csdn.net/20150721165857300)
![Work time curve](http://img.blog.csdn.net/20150721165836128)

### 工作计划
User can check and manage time sheet for a week for different task and activities. Users can record their work time for different job to help them manage their time.

- User can browse time sheet records for weeks in “Timesheet” page.
![Time sheet](http://img.blog.csdn.net/20150721170101251)

- After clicking “view”, user can check all time sheet for that week. User can check employee work time for different task and activity in different day.
![Time sheet view](http://img.blog.csdn.net/20150721170150993)

- User can add time sheet for different week by clicking “Add Timesheet” link or add time sheet record for this week by clicking “Add” button.

### 任务列表
User can manage tasks and activities in “Task Info” page. These tasks and activities will be used when user create time sheet for different tasks and activities.

- User can check all tasks in the page.
![Tasks List](http://img.blog.csdn.net/20150721170407097)

- User can edit task information by clicking task name. Then user can edit task information including name, admins, description and activities belonged to this task.
![Task edit](http://img.blog.csdn.net/20150721170437957)

## 评价系统
评价系统包括6部分功能：

- 评价活动
- 评价反馈
- 技能列表
- 目标列表
- 评价表格
- 评价表格配置

“Performance” consists of evaluation trackers management for different employees.

### 评价活动
User can manage trackers for different evaluation activities, including creating evaluation activity for employee and assigning reviews for the activity.
![Evaluate Activity](http://img.blog.csdn.net/20150721171456221)

### 评价反馈
User can give evaluation feedback for employee by adding tracker log for evaluation tracker in “Employee Trackers” page.
![Evaluation Feedback](http://img.blog.csdn.net/20150721171701775)
![Evaluation Feedback](http://img.blog.csdn.net/20150721171718939)

### 技能列表
User can check and manage competencies information in “Competencies” menu of “Performance” category.

### 目标列表
User can check and manage goals information in “Goals” menu of “Performance” category.

### 评价表格
User can create appraisal form for employee based on competencies, goals and asking questions. User can also check and manage appraisal records in “Appraisal” menu of “Performance” category.
![Evaluation Form](http://img.blog.csdn.net/20150721171902508)

- User can create an appraisal form for an employee by clicking the “Add” button in “Appraisal List”. Then user can input basic information for this appraisal.
![Add Appraisal](http://img.blog.csdn.net/20150721171945143)

- After inputting basic information, user can create appraisal form by clicking “Manage” menu in “Actions” column.
![Manage Appraisal](http://img.blog.csdn.net/20150721172024235)

- Then user can choose which competencies, goals and what questions will be evaluated and what ratio for each part in this appraisal. User can also update basic information and evaluators by choose tabs in the left.
![View appraisal](http://img.blog.csdn.net/20150721172124595)

- After design the appraisal form, user can evaluate the employee by clicking “Evaluate” menu in “Actions” column.
![Manage Appraisal](http://img.blog.csdn.net/20150721172024235)

- User can rate for different competencies, goals and questions in different section.
![Review](http://img.blog.csdn.net/20150721172256303)

- After evaluating, user can check the appraisal information by clicking “View” menu in “Actions” column.
![Review all](http://img.blog.csdn.net/20150721172330514)

- User can change the appraisal configuration in “Configuration” menu of “Performance” category.

## 日程管理
“My Schedule” consists of schedule management page for employee. Users can click on schedule to add or update their event and manage their event on the right event list.
![Schedule](http://img.blog.csdn.net/20150721172447082)

User can add schedule event by clicking specific date of “Add” button above the “Event List”. User can choose “Notify Time” and “Email Notification” for the event. When the due date is coming, the system will give notification and send email to user. The event need to be done will be marked as a different color.
![Add event](http://img.blog.csdn.net/20150721172548924)
![Effect](http://img.blog.csdn.net/20150721172558431)

## 个人信息管理
“My Info” consists of management page for employee personal and job information. Users can change photos and update their personal and job information in “My Info” page.

## 权限管理
The system also has user authorizations management that users with different roles have different privilege to different pages.

英文内容是在写STM文档时的内容，未来慢慢翻译（也许也不用翻译）。
具体代码请见[Github](https://github.com/kaelsass/newspaper-management-system)。
