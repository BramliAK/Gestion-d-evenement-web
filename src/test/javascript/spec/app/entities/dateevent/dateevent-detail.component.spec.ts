/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BackendTestModule } from '../../../test.module';
import { DateeventDetailComponent } from 'app/entities/dateevent/dateevent-detail.component';
import { Dateevent } from 'app/shared/model/dateevent.model';

describe('Component Tests', () => {
    describe('Dateevent Management Detail Component', () => {
        let comp: DateeventDetailComponent;
        let fixture: ComponentFixture<DateeventDetailComponent>;
        const route = ({ data: of({ dateevent: new Dateevent(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BackendTestModule],
                declarations: [DateeventDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DateeventDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DateeventDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.dateevent).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
