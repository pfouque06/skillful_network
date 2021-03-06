import { User } from './user';
import { JobOffer } from './job-offer';
import { Training } from './training';
import { IExam, MOCK_EXAM, IExamForm, MOCK_EXAM_FORM } from './mock.examen';
import { Exam } from './Exam';
import { ExamForm } from './ExamForm';
import { Result } from './Result';

export class Simulation {

    private _id: number;
    private _userId: number;
    private _jobGoal: String;
    private _creationDate: Date ;
    private _synthesis: String ;
    private _results: Result[];
    private _jobOffer: JobOffer;
    private _jobAccess: boolean;
    private _training: Training;
    // private _exam: IExam;
    // private _examForm: IExamForm;
    private _exam: Exam;
    private _simulationForm: ExamForm;

    constructor(data: any) {
        this._id = data.id;
        this._userId = data.user.id;
        this._jobGoal = data.jobGoal;
        this._creationDate = data.creationDate;
        this._synthesis = data.synthesis;
        this._results = data.results;
        this._jobOffer = data.jobOffer;
        this._jobAccess = data.jobAccess;
        this._training = data.training;
        this._exam = data.exam;
        this._simulationForm = data.simulationForm;
    }
    
    public get id(): number {
        return this._id;
    }
    public set id(value: number) {
        this._id = value;
    }
    public get userId(): number {
        return this._userId;
    }
    public set userId(value: number) {
        this._userId = value;
    }
    public get jobGoal(): String {
        return this._jobGoal;
    }
    public set jobGoal(value: String) {
        this._jobGoal = value;
    }
    public get creationDate(): Date {
        return this._creationDate;
    }
    public set creationDate(value: Date) {
        this._creationDate = value;
    }
    public get synthesis(): String {
        return this._synthesis;
    }
    public set synthesis(value: String) {
        this._synthesis = value;
    }
    public get results(): Result[] {
        return this._results;
    }
    public set results(value: Result[]) {
        this._results = value;
    }
    public get jobOffer(): JobOffer {
        return this._jobOffer;
    }
    public set jobOffer(value: JobOffer) {
        this._jobOffer = value;
    }
    public get training(): Training {
        return this._training;
    }
    public set training(value: Training) {
        this._training = value;
    }
    public get jobAccess(): boolean {
        return this._jobAccess;
    }
    public set jobAccess(value: boolean) {
        this._jobAccess = value;
    }
    public get exam(): Exam {
        return this._exam;
    }
    public set exam(value: Exam) {
        this._exam = value;
    }
    public get simulationForm(): ExamForm {
        return this._simulationForm;
    }
    public set simulationForm(value: ExamForm) {
        this._simulationForm = value;
    }

}